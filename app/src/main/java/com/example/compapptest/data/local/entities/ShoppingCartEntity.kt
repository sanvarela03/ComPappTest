package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.request.ItemRequest

@Entity
data class ShoppingCartEntity(
    @PrimaryKey(autoGenerate = false)
    val productId: Long,
    val name: String,
    val description: String,
    val price: Double,
    val units: Int,
    val producerId: Long,
) {
    fun toItemRequest(): ItemRequest {
        return ItemRequest(
            productId = productId,
            units = units,
        )
    }
}