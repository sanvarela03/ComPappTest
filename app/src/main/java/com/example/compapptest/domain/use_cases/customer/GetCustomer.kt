package com.example.compapptest.domain.use_cases.customer

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse
import com.example.compapptest.domain.repository.CustomerRepository
import kotlinx.coroutines.flow.Flow

class GetCustomer(
    private val repository: CustomerRepository
) {
    operator fun invoke(
        fetchFromRemote: Boolean,
        id: Long
    ): Flow<ApiResponse<CustomerInfoResponse>> {
        return repository.loadCustomer(fetchFromRemote, id)
    }
}