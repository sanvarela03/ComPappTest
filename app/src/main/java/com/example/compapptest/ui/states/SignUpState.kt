package com.example.compapptest.ui.states

import com.example.compapptest.data.remote.payload.request.SignUpRequest

data class SignUpState(
    var firstName: String = "",
    var lastName: String = "",
    var username: String = "",
    var email: String = "",
    var phone: String = "",
    var password: String = "",
    var privacyPolicyAccepted: Boolean = false,

    var firstNameError: Boolean = false,
    var lastNameError: Boolean = false,
    var emailError: Boolean = false,
    var usernameError: Boolean = false,
    var phoneError: Boolean = false,
    var passwordError: Boolean = false,
    var privacyPolicyError: Boolean = false,

    var showDialog: Boolean = false
){
    fun toSignUpRequest(firebaseToken: String): SignUpRequest {
        return SignUpRequest(
            username = username,
            name = firstName,
            lastName = lastName,
            email = email,
            phone = phone,
            firebaseToken = firebaseToken,
            password = password
        )
    }
}
