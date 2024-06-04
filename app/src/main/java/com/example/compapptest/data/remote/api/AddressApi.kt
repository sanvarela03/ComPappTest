package com.example.compapptest.data.remote.api

import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.request.UpdateAddressRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AddressApi {
    @POST("/v1/api/users/address")
    suspend fun addAddress(@Body addressRequest: AddressRequest): Response<MessageResponse>

    @PUT("/v1/api/users/{userId}/address/{addressId}")
    suspend fun updateAddress(
        @Path("userId") userId: Long,
        @Path("addressId") addressId: Long,
        @Body updateAddressRequest: UpdateAddressRequest
    ): Response<MessageResponse>

    @DELETE("/v1/api/users/address/{addressId}")
    suspend fun deleteAddress(@Path("addressId") id: Long): Response<MessageResponse>

    @GET("/v1/api/users/{userId}/address")
    suspend fun getAllAddress(@Path("userId") id: Long): Response<List<AddressResponse>>
}