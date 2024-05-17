package com.example.compapptest.data.remote.payload.request

data class UpdateAddressRequest(
    val id: Long,
    val name: String,
    val street: String,
    val instruction: String,
    val isCurrentAddress: Boolean,
    val latitude: Double,
    val longitude: Double,
)
