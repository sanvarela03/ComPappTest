package com.example.compapptest.data.remote.payload.response

data class ErrorResponse(
    val path: String,
    val error: String,
    val message: String,
    val status: Int
)
