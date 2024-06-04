package com.example.compapptest.data.repository

import android.util.Log
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.config.common.apiRequestFlow
import com.example.compapptest.data.local.dao.AddressDao
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.remote.api.AddressApi
import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.request.UpdateAddressRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.AddressRepository
import com.example.protapptest.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddressRepositoryImpl @Inject constructor(
    private val addressApi: AddressApi,
    private val addressDao: AddressDao,
    private val customerDao: CustomerDao,
    private val tokenManager: TokenManager
) : AddressRepository {
    override suspend fun addNewAddress(addressRequest: AddressRequest): Flow<ApiResponse<MessageResponse>> {
        return apiRequestFlow { addressApi.addAddress(addressRequest) }
    }

    override suspend fun updateAddress(updateAddressRequest: UpdateAddressRequest): Flow<ApiResponse<MessageResponse>> {
        Log.d("AddressRepositoryImpl", "updateAddressRequest =$updateAddressRequest")
        val userId = tokenManager.getUserId().first()
        return apiRequestFlow {
            addressApi.updateAddress(
                userId = userId ?: -1L,
                addressId = updateAddressRequest.id,
                updateAddressRequest = updateAddressRequest
            )
        }
    }

    override suspend fun deleteAddress(addressId: Long): Flow<ApiResponse<MessageResponse>> {
        return apiRequestFlow {
            addressApi.deleteAddress(addressId)
        }
    }

    override suspend fun getAllAddresses(
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<List<AddressEntity>>> {
        return flow {
            emit(ApiResponse.Loading)
            val userId = tokenManager.getUserId().first()
            userId?.let {
                val customerWithAddress = customerDao.getCustomerAndAddress(userId)

                Log.d("manzana", "customerWithAddress = $customerWithAddress")

                if (customerWithAddress.isNotEmpty()) {
                    val localAddresses = customerWithAddress[0].addressList
                    emit(ApiResponse.Success(localAddresses))
                }

                val isDbEmpty = customerWithAddress.isEmpty()

                val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

                if (shouldJustLoadFromCache) {
                    return@flow
                }

//                val remote = getRemote(it)

                val remoteTest = apiRequestFlow { addressApi.getAllAddress(it) }

                remoteTest.collect { response ->
                    when (response) {
                        ApiResponse.Waiting -> {}
                        ApiResponse.Loading -> {}
                        is ApiResponse.Failure -> {
                            emit(response)
                        }

                        is ApiResponse.Success -> {
                            addressDao.clearAddressEntity()
                            val remoteAddresses = response.data.map { it.toAddressEntity() }
                            addressDao.insertAllAddress(remoteAddresses)
                            val localAddresses = addressDao.getAllAddressesByUserId(userId)

                            if (localAddresses != null) {
                                emit(ApiResponse.Success(localAddresses))
                            }
                        }
                    }
                }

//                remote?.let { addresesResponse ->
//                    customerDao.clearAddressEntity()
//                    val remoteAddresses = addresesResponse
//
//                    remoteAddresses.forEach {
//                        Log.d("AddressRepositoryImpl", "getAllAddresses | insert : $it")
//                        customerDao.insertAddress(it.toAddressEntity())
//                    }
//
//                    val addresses = customerDao.getAllAddresses()
//
//                    Log.d("Addresses", "addresses $addresses")
//                    val newCustomerWithAddress = customerDao.getCustomerAndAddress(userId)
//                    Log.d("Addresses", "addresses $newCustomerWithAddress")
//                    Log.d(
//                        "Addresses",
//                        "customer and address : ${customerDao.getCustomerAndAddressTest()}"
//                    )
//
//                    newCustomerWithAddress.let {
//                        val newLocalAddresses = newCustomerWithAddress[0].addressList
//                        emit(ApiResponse.Success(newLocalAddresses))
//                    }
//                }
            }
        }
    }

    private suspend fun FlowCollector<ApiResponse<List<AddressEntity>>>.getRemote(
        it: Long
    ) = try {
        val response = addressApi.getAllAddress(it)
        if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    } catch (e: IOException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    } catch (e: HttpException) {
        e.printStackTrace()
        emit(ApiResponse.Failure("No se pudo cargar los datos", 400))
        null
    }

    override suspend fun getAddressById(id: Long): AddressEntity? {
        return customerDao.getAddress(id)
    }

    override suspend fun getCurrentAddressId(producerId: Long): Long? {
        return customerDao.getCurrentAddressId(producerId)
    }
}