package com.example.compapptest.ui.events

import android.content.Context

sealed class SignInEvent {
    data class UsernameChanged(val username: String) : SignInEvent()
    data class PasswordChanged(val password: String) : SignInEvent()
    data class LoginButtonClicked(val context : Context) : SignInEvent()
}