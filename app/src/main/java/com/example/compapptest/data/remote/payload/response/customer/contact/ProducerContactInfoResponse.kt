package com.example.compapptest.data.remote.payload.response.customer.contact

data class ProducerContactInfoResponse(
    val producerId: Long,
    val completeName: String,
    val phone: String,
    val email: String
)
