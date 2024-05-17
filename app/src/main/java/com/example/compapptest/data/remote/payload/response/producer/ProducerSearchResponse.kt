package com.example.compapptest.data.remote.payload.response.producer

import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse

data class ProducerSearchResponse(
    val producerId: Long,
    val name: String,
    val lastName: String,
    val email: String,
    val phone: String?,
    val currentAddress: AddressResponse,
) {
    fun toProducerEntity(): ProducerEntity {
        return ProducerEntity(
            producerId = producerId,
            name = name,
            lastName = lastName,
            email = email,
            phone = if (phone != null) phone else "NaN",
            currentAddressId = currentAddress.id,
        )
    }
}