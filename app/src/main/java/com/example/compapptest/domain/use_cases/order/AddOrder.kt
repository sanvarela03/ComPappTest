package com.example.compapptest.domain.use_cases.order

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.data.remote.payload.request.AddOrderRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class AddOrder(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(addOrderRequest: AddOrderRequest): Flow<ApiResponse<MessageResponse>> {
        return repository.addOrder(addOrderRequest)
    }
}