package com.example.compapptest.domain.use_cases.address

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class DeleteAddress(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(addressId: Long): Flow<ApiResponse<MessageResponse>> {
        return repository.deleteAddress(addressId)
    }
}