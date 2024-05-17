package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val description: String,
    val weight: Double,
    val length: Double,
    val width: Double,
    val height: Double,
    val price: Double,
    val unitsAvailable: Int,
    val available: Boolean,
    val producerId: Long
) {
    fun toShoppingCartEntity(units: Int): ShoppingCartEntity {
        return ShoppingCartEntity(
            productId = id,
            name = name,
            description = description,
            price = price,
            producerId = producerId,
            units = units,
        )
    }
}
