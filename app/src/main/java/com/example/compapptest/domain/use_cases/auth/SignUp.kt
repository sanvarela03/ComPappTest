package com.example.protapptest.domain.use_cases.auth

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.request.SignUpRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class SignUp(
    private val repository: AuthRepository
) {
    operator fun invoke(signUpRequest: SignUpRequest): Flow<ApiResponse<MessageResponse>> {
        return repository.signUp(signUpRequest)
    }
}