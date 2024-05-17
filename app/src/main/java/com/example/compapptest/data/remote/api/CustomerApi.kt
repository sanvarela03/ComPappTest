package com.example.compapptest.data.remote.api

import com.example.compapptest.data.remote.payload.request.AddOrderRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse
import com.example.compapptest.data.remote.payload.response.customer.order.OrderInfoResponse
import com.example.compapptest.data.remote.payload.response.producer.ProducerDetailResponse
import com.example.compapptest.data.remote.payload.response.producer.ProducerSearchResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface CustomerApi {
    @GET("v1/api/customers/{userId}")
    suspend fun getCustomer(@Path("userId") id: Long): Response<CustomerInfoResponse>

    @GET("v1/api/customers/{userId}/orders")
    suspend fun getAllOrders(@Path("userId") id: Long): Response<List<OrderInfoResponse>>


    @POST("v1/api/customers/orders/order")
    suspend fun addOrder(@Body addOrderRequest: AddOrderRequest): Response<MessageResponse>

    @GET("v1/api/customers/{customerId}/search-producers")
    suspend fun searchProducers(
        @Path("customerId") customerId: Long,
        @Query("city") city: String = "BOG"
    ): Response<List<ProducerSearchResponse>>

    @GET("v1/api/customers/{customerId}/search-producers/{producerId}")
    suspend fun searchProducer(
        @Path("customerId") customerId: Long,
        @Path("producerId") producerId: Long
    ): Response<ProducerDetailResponse>
}