package com.example.compapptest.domain.use_cases.address

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.domain.repository.AddressRepository
import kotlinx.coroutines.flow.Flow

class GetAllAddresses(
    private val repository: AddressRepository
) {
    suspend operator fun invoke(fetchFromRemote: Boolean): Flow<ApiResponse<List<AddressEntity>>> {
        return repository.getAllAddresses(fetchFromRemote)
    }
}