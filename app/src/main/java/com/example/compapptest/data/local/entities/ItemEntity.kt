package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ItemEntity(
    @PrimaryKey(autoGenerate = false)
    val productId: Long,
    val name: String,
    val description: String,
    val units: Int,
    val price: Double,
    val orderId: Long,
)
