package com.example.compapptest.domain.repository

import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.request.SignInRequest
import com.example.compapptest.data.remote.payload.request.SignUpRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.SignInResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun signIn(signInRequest: SignInRequest): Flow<ApiResponse<SignInResponse>>
    fun signOut(): Flow<ApiResponse<MessageResponse>>
    fun signUp(signUpRequest: SignUpRequest): Flow<ApiResponse<MessageResponse>>
    fun authenticate(): Flow<Boolean>
}