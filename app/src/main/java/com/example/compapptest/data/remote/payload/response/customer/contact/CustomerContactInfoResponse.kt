package com.example.compapptest.data.remote.payload.response.customer.contact

data class CustomerContactInfoResponse(
    val customerId: Long,
    val completeName: String,
    val phone: String,
    val email: String
)