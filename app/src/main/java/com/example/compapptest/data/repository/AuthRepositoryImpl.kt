package com.example.compapptest.data.repository

import android.util.Log
import com.example.compapptest.config.common.apiRequestFlow
import com.example.compapptest.data.remote.api.AuthApi
import com.example.compapptest.data.remote.payload.request.SignInRequest
import com.example.compapptest.data.remote.payload.request.SignUpRequest
import com.example.compapptest.domain.repository.AuthRepository
import com.example.protapptest.security.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager
) : AuthRepository {

    val TAG = AuthRepositoryImpl::class.simpleName
    override fun signIn(signInRequest: SignInRequest) = apiRequestFlow {
        Log.d("login(auth: AuthRequest) -> auth: ", "$signInRequest")
        authApi.signIn(signInRequest)
    }

    override fun signOut() = apiRequestFlow {
        authApi.signOut()
    }

    override fun signUp(signUpRequest: SignUpRequest) = apiRequestFlow {
        authApi.signUp(signUpRequest)
    }

    override fun authenticate(): Flow<Boolean> = flow {
        tokenManager.getAccessToken().collect {
            if (it != null) {
                emit(true)
            } else {
                emit(false)
            }
        }
    }
}