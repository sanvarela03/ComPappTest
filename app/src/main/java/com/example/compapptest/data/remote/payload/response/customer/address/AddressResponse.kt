package com.example.compapptest.data.remote.payload.response.customer.address

import com.example.compapptest.data.local.entities.AddressEntity

data class AddressResponse(
    val id: Long,
    val name: String,
    val street: String,
    val instruction: String,
    val latitude: Double,
    val longitude: Double,
    val city: String,
    val state: String,
    val country: String,
    val userId: Long
) {
    fun toAddressEntity(): AddressEntity {
        return AddressEntity(
            id = id,
            name = name,
            street = street,
            instruction = instruction,
            latitude = latitude,
            longitude = longitude,
            city = city,
            state = state,
            country = country,
            userId = userId
        )
    }
}
