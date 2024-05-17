package com.example.compapptest.domain.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.request.UpdateAddressRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import kotlinx.coroutines.flow.Flow

interface AddressRepository {
    suspend fun addNewAddress(addressRequest: AddressRequest): Flow<ApiResponse<MessageResponse>>
    suspend fun updateAddress(updateAddressRequest: UpdateAddressRequest): Flow<ApiResponse<MessageResponse>>
    suspend fun deleteAddress(addressId: Long): Flow<ApiResponse<MessageResponse>>
    suspend fun getAllAddresses(
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<List<AddressEntity>>>

    suspend fun getAddressById(id: Long): AddressEntity?
    suspend fun getCurrentAddressId(producerId: Long): Long?
}