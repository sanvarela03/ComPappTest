package com.example.compapptest.ui.states

import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.relations.OrderAndStatus

data class OrderListState(
    val orderList: List<OrderAndStatus> = emptyList(),
    val isRefreshing: Boolean = false,
)