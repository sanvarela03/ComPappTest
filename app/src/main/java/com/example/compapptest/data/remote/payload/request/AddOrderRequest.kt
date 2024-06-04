package com.example.compapptest.data.remote.payload.request

data class AddOrderRequest(
    val producerId: Long,
    val deliveryAddressId: Long,
    val paymentMethod: String,
    val estimatedDistance: Double,
    val estimatedTime: Double,
    val products: List<ItemRequest>
)
