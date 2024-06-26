package com.example.compapptest.data.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.config.common.apiRequestFlow
import com.example.compapptest.data.local.dao.CustomerDao
import com.example.compapptest.data.local.dao.ShoppingCartDao
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.data.remote.api.CustomerApi
import com.example.compapptest.data.remote.payload.request.AddOrderRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse
import com.example.compapptest.domain.repository.OrderRepository
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
class OrderRepositoryImpl @Inject constructor(
    private val customerDao: CustomerDao,
    private val customerApi: CustomerApi,
    private val shoppingCartDao: ShoppingCartDao,
    private val tokenManager: TokenManager
) : OrderRepository {

    override suspend fun getAllOrdersWithStatus(fetchFromRemote: Boolean): Flow<ApiResponse<List<OrderAndStatus>>> =
        flow {
            emit(ApiResponse.Loading)
            val customerId = tokenManager.getUserId().first()

            customerId?.let { id ->
                val localOrderAndStatus = customerDao.getOrderAndStatus()

                if (localOrderAndStatus != null) {
                    emit(ApiResponse.Success(localOrderAndStatus))
                }

                val isDbEmpty = localOrderAndStatus == null

                val shouldJustLoadFromCache = !isDbEmpty && !fetchFromRemote

                if (shouldJustLoadFromCache) {
                    return@flow
                }

                val remote = getRemote(id)

                remote?.let { orderInfoResponseList: List<OrderInfoResponse> ->
                    customerDao.clearOrderEntity()
                    customerDao.clearCustomerInfoEntity()
                    customerDao.clearProducerInfoEntity()
                    customerDao.clearTransporterInfoEntity()
                    customerDao.clearPickupAddressEntity()
                    customerDao.clearDeliveryAddressEntity()
                    customerDao.clearStatusEntity()

                    orderInfoResponseList.forEach {
                        customerDao.insertOrder(it.toOrderEntity())
                        customerDao.insertCustomerInfoEntity(it.toCustomerInfoEntity())
                        customerDao.insertProducerInfoEntity(it.toProducerInfoEntity())
                        if (it.transporterContactInfoResponse!!.transporterId != null) {
                            customerDao.insertTransporterInfoEntity(it.toTransporterInfoEntity())
                        }
                        customerDao.insertPickupAddress(it.toPickupAddressEntity())
                        customerDao.insertDeliveryAddress(it.toDeliveryAddressEntity())

                        it.statusList.forEach { statusResponse ->
                            customerDao.insertStatus(statusResponse.toStatusEntity())
                        }
                    }
                }

                val newOrderAndStatus = customerDao.getOrderAndStatus()

                newOrderAndStatus?.let {
                    emit(ApiResponse.Success(newOrderAndStatus))
                }
            }
        }

    override suspend fun addOrder(addOrderRequest: AddOrderRequest): Flow<ApiResponse<MessageResponse>> {
        return apiRequestFlow {
            shoppingCartDao.clearShoppingCartEntity(producerId = addOrderRequest.producerId)
            customerApi.addOrder(addOrderRequest)
        }
    }

    private suspend fun FlowCollector<ApiResponse<List<OrderAndStatus>>>.getRemote(
        id: Long
    ) = try {
        val response = customerApi.getAllOrders(id)
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