package com.example.protapptest.domain.use_cases.auth

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.request.SignInRequest
import com.example.compapptest.data.remote.payload.response.SignInResponse
import com.example.compapptest.domain.repository.AuthRepository

import kotlinx.coroutines.flow.Flow

class SignIn(
    private val repository: AuthRepository
) {
    operator fun invoke(username: String, password: String): Flow<ApiResponse<SignInResponse>> {
        return repository.signIn(SignInRequest(username, password))
    }
}