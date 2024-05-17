package com.example.compapptest.ui.events

sealed class SignUpEvent {
    data class FirstNameChanged(val firstName: String) : SignUpEvent()
    data class LastNameChanged(val lastName: String) : SignUpEvent()
    data class UsernameChanged(val username: String) : SignUpEvent()
    data class EmailChanged(val email: String) : SignUpEvent()
    data class PhoneChanged(val phone: String) : SignUpEvent()
    data class PasswordChanged(val password: String) : SignUpEvent()

    data class PrivacyPolicyCheckBoxClicked(val status: Boolean) : SignUpEvent()

    object RegisterButtonClicked : SignUpEvent()
}