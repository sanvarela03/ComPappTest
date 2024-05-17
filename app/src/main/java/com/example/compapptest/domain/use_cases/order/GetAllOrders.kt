package com.example.compapptest.domain.use_cases.order

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.relations.OrderAndStatus
import com.example.compapptest.domain.repository.OrderRepository
import kotlinx.coroutines.flow.Flow

class GetAllOrders(
    private val repository: OrderRepository
) {
    suspend operator fun invoke(fetchFromRemote: Boolean): Flow<ApiResponse<List<OrderAndStatus>>> {
        return repository.getAllOrdersWithStatus(fetchFromRemote)
    }
}