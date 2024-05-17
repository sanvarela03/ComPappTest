package com.example.protapptest.security

import android.util.Log
import com.example.compapptest.config.common.HOST_URL
import com.example.compapptest.data.remote.api.AuthApi
import com.example.compapptest.data.remote.payload.request.RefreshRequest
import com.example.compapptest.data.remote.payload.response.RefreshResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class AuthAuthenticator
@Inject
constructor(
    private val tokenManager: TokenManager
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        }

        return runBlocking {
            val newToken = getNewAccessToken(refreshToken)

            if (!newToken.isSuccessful || newToken.body() == null) {
                tokenManager.deleteAccessToken()
            }

            newToken.body()?.let {
                tokenManager.saveAccessToken(it.accessToken)
                response.request
                    .newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }

    private suspend fun getNewAccessToken(refreshToken: String?): retrofit2.Response<RefreshResponse> {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val okHttpClient = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(HOST_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        val service = retrofit.create(AuthApi::class.java)
        Log.d("REFRESH TOOOOKEN: ", "$refreshToken")
        val refreshTokenRequest = RefreshRequest(refreshToken!!)
        return service.refreshToken(refreshTokenRequest)
    }
}