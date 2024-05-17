package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderEntity(
    @PrimaryKey(autoGenerate = false)
    val orderId: Long,
    val orderCost: Double,
    val orderWeight: Double,
    val orderVolume: Double,
    val estimatedTravelDistance: Double,
    val estimatedTravelDuration: Double,
    val shippingCost: Double,
    val paymentMethod: String,
    val maxDeliveryDate: String,
    val estimatedPickupDate: String?,
    val transporterId : Long?,
    val customerId: Long,
    val producerId: Long,
)
