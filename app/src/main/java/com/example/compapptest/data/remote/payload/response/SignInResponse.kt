package com.example.compapptest.data.remote.payload.response

data class SignInResponse(
    var id: Long,
    val username: String,
    val email: String,
    val roles: List<String>,
    val token: String,
    val type: String,
    val refreshToken: String
)
