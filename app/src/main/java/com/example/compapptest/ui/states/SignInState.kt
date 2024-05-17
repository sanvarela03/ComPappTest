package com.example.compapptest.ui.states

data class SignInState(
    var username: String = "",
    var password: String = "",

    var usernameError: Boolean = false,
    var passwordError: Boolean = false
)
