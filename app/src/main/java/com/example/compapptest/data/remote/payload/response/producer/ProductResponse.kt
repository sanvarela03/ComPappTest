package com.example.compapptest.data.remote.payload.response.producer

import com.example.compapptest.data.local.entities.ProductEntity

data class ProductResponse(
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
    fun toProductEntity(): ProductEntity {
        return ProductEntity(
            id = id,
            name = name,
            description = description,
            weight = weight,
            length = length,
            width = width,
            height = height,
            price = price,
            unitsAvailable = unitsAvailable,
            available = available,
            producerId = producerId,
        )
    }
}
