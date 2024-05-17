package com.example.compapptest.domain.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.data.remote.payload.request.AddOrderRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import kotlinx.coroutines.flow.Flow

interface OrderRepository {
    suspend fun getAllOrdersWithStatus(
        fetchFromRemote: Boolean
    ): Flow<ApiResponse<List<OrderAndStatus>>>

    suspend fun addOrder(addOrderRequest: AddOrderRequest): Flow<ApiResponse<MessageResponse>>
}