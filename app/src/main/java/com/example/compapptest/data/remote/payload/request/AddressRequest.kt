package com.example.compapptest.data.remote.payload.request

data class AddressRequest(
    val name: String,
    val country: String,
    val state: String,
    val city: String,
    val street: String,
    val instruction: String,
    val isCurrentAddress: Boolean,
    val latitude: Double,
    val longitude: Double,
)
