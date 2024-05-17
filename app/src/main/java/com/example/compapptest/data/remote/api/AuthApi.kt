package com.example.compapptest.data.remote.api

import com.example.compapptest.data.remote.payload.request.RefreshRequest
import com.example.compapptest.data.remote.payload.request.SignInRequest
import com.example.compapptest.data.remote.payload.request.SignUpRequest
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.RefreshResponse
import com.example.compapptest.data.remote.payload.response.SignInResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/v1/api/auth/signin")
    suspend fun signIn(@Body signInRequest: SignInRequest): Response<SignInResponse>

    @POST("/v1/api/auth/refreshtoken")
    suspend fun refreshToken(@Body refreshRequest: RefreshRequest): Response<RefreshResponse>

    @POST("/v1/api/auth/signup")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): Response<MessageResponse>

    @POST("/v1/api/auth/signout")
    suspend fun signOut(): Response<MessageResponse>
}