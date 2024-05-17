package com.example.compapptest.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse

@Entity
data class AddressEntity(
    @PrimaryKey(autoGenerate = false)
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
    fun toAddressResponse(): AddressResponse {
        return AddressResponse(
            id = id,
            name = name,
            street = street,
            instruction = instruction,
            latitude = latitude,
            longitude = longitude,
            city = city,
            state = state,
            country = country,
            userId = userId,
        )
    }
}
