package com.example.protapptest.domain.use_cases.auth

data class AuthUseCases(
    val signIn: SignIn,
    val signOut: SignOut,
    val signUp: SignUp,
    val authenticate: Authenticate
)