package com.example.compapptest.data.remote.payload.request

data class SignUpRequest(
    val username: String,
    val name: String,
    val lastName: String,
    val email: String,
    val password: String,
    val phone: String,
    val firebaseToken: String,
    private val role: List<String> = listOf("user", "cust")
)