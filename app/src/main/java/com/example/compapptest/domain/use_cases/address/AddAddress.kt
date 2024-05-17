package com.example.compapptest.domain.use_cases.address

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.request.AddressRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class AddAddress(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressRequest: AddressRequest): Flow<ApiResponse<MessageResponse>> {
        return repository.addNewAddress(addressRequest)
    }
}