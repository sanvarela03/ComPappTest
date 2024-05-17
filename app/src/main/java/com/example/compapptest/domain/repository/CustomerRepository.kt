package com.example.compapptest.domain.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse
import kotlinx.coroutines.flow.Flow

interface CustomerRepository {
    fun loadCustomer(
        fetchFromRemote: Boolean,
        id: Long
    ): Flow<ApiResponse<CustomerInfoResponse>>
}