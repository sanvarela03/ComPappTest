package com.example.compapptest.data.repository

import android.util.Log
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.entities.CustomerEntity
import com.example.compapptest.data.remote.api.CustomerApi
import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse
import com.example.compapptest.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val api: CustomerApi,
    private val dao: CustomerDao
) : CustomerRepository {
    override fun loadCustomer(
        fetchFromRemote: Boolean,
        id: Long
    ): Flow<ApiResponse<CustomerInfoResponse>> = flow {

        emit(ApiResponse.Loading)

        val localProducerAndAddress = dao.getCustomerAndAddress(id)

        val localCustomer: CustomerEntity? = dao.getCustomer(id)

        if (localCustomer != null) {
            val local = localCustomer.toCustomerInfoResponse(
                addressList = localProducerAndAddress[0].addressList
            )

            emit(ApiResponse.Success(local))
        }

        val isDbEmpty = localCustomer == null

        val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

        if (shouldJustLoadFromCache) {
            return@flow
        }

        val remote = getRemote(id)

        remote?.let { response ->
            dao.clearAddressEntity()
            dao.clearCustomerEntity()
            dao.clearOrderEntity()
            dao.clearCustomerInfoEntity()
            dao.clearProducerInfoEntity()
            dao.clearTransporterInfoEntity()
            dao.clearPickupAddressEntity()
            dao.clearDeliveryAddressEntity()
            dao.clearStatusEntity()

            dao.insertCustomer(response.toCustomerEntity())

            response.addressList.forEach { addressResponse ->
                dao.insertAddress(addressResponse.toAddressEntity())
            }

            response.orderInfoResponseList.forEach { orderInfoResponse ->


                Log.d("ROLO", "Customer Info : ${orderInfoResponse.customerContactInfoResponse}")
                Log.d(
                    "ROLO",
                    "Customer Info (toCustomerInfoEntity) : ${orderInfoResponse.toCustomerInfoEntity()}"
                )
                dao.insertOrder(orderInfoResponse.toOrderEntity())
                dao.insertProducerInfoEntity(orderInfoResponse.toProducerInfoEntity())
                dao.insertCustomerInfoEntity(orderInfoResponse.toCustomerInfoEntity())
                val transporterExists =
                    orderInfoResponse.transporterContactInfoResponse?.transporterId != null
                if (transporterExists) {
                    dao.insertTransporterInfoEntity(orderInfoResponse.toTransporterInfoEntity())
                }

                dao.insertDeliveryAddress(orderInfoResponse.toDeliveryAddressEntity())
                dao.insertPickupAddress(orderInfoResponse.toPickupAddressEntity())

                orderInfoResponse.statusList.forEach { statusResponse ->
                    dao.insertStatus(statusResponse.toStatusEntity())
                }
            }

            val newLocalCustomer = dao.getCustomer(response.customerId)


            val localAddress =
                dao.getCustomerAndAddress(response.customerId)[0].addressList


            val localOrderList =
                dao.getOrderAndStatus()


            Log.d("MANGO", "localOrderList = $localOrderList")
            Log.d("MANGO", "localOrderList = ${dao.getCustomerAndAddress(response.customerId)}")
            Log.d("MANGO", "localOrderList = ${dao.getCustomerInfo(response.customerId)}")


            val orderList = localOrderList?.map { orderAndStatus ->
                orderAndStatus.toOrderInfoResponse(
                    customerContactInfoResponse = dao.getCustomerInfo(orderAndStatus.order.customerId)
                        .toCustomerContactInfoResponse(),
                    producerContactInfoResponse = dao.getProducerInfo(orderAndStatus.order.producerId)
                        .toProducerContactInfoResponse(),
                    transporterContactInfoResponse =
                    if (orderAndStatus.order.transporterId != null) {
                        dao.getTransporterInfo(orderAndStatus.order.transporterId)
                            .toTransporterContactInfoResponse()
                    } else {
                        null
                    },
                    deliveryAddress = dao.getDeliveryAddress(orderAndStatus.order.orderId)!!
                        .toAddressResponse(),
                    pickupAddress = dao.getPickupAddress(orderAndStatus.order.orderId)!!
                        .toAddressResponse(),
                )
            }



            newLocalCustomer?.let {
                emit(ApiResponse.Success(it.toCustomerInfoResponse(localAddress, orderList)))
            }
        }


    }

    private suspend fun FlowCollector<ApiResponse<CustomerInfoResponse>>.getRemote(
        id: Long
    ) = try {
        val response = api.getCustomer(id)
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
}