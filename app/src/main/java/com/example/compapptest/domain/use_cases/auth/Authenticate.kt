package com.example.protapptest.domain.use_cases.auth

import android.util.Log
import com.example.compapptest.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow

class Authenticate(
    private val repository: AuthRepository
) {
    val TAG = Authenticate::class.simpleName
    suspend operator fun invoke(): Flow<Boolean> {
        Log.d(TAG, "INSIDE INVOKE")
        return repository.authenticate()
    }
}