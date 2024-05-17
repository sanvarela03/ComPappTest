package com.example.compapptest.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.SignInResponse
import com.example.compapptest.ui.events.SignUpEvent
import com.example.compapptest.ui.rules.Validator
import com.example.compapptest.ui.states.SignUpState
import com.example.protapptest.domain.use_cases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authUseCases: AuthUseCases

) : ViewModel() {
    private val TAG = SignUpViewModel::class.simpleName

    var state by mutableStateOf(SignUpState())

    var allValidationsPassed by mutableStateOf(false)

    private val _signUpResponse =
        MutableStateFlow<ApiResponse<MessageResponse>>(ApiResponse.Waiting)
    val signUpResponse = _signUpResponse.asStateFlow()

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UsernameChanged -> {
                state = state.copy(
                    username = event.username
                )
            }

            is SignUpEvent.FirstNameChanged -> {
                state = state.copy(
                    firstName = event.firstName
                )
                printState()
            }

            is SignUpEvent.LastNameChanged -> {
                state = state.copy(
                    lastName = event.lastName
                )
                printState()
            }

            is SignUpEvent.EmailChanged -> {
                state = state.copy(
                    email = event.email
                )
                printState()
            }

            is SignUpEvent.PhoneChanged -> {
                state = state.copy(phone = event.phone)
                printState()
            }

            is SignUpEvent.PasswordChanged -> {
                state = state.copy(
                    password = event.password
                )
                printState()
            }

            is SignUpEvent.PrivacyPolicyCheckBoxClicked -> {
                state = state.copy(
                    privacyPolicyAccepted = event.status
                )
            }

            SignUpEvent.RegisterButtonClicked -> {
                signUp()
            }

        }
        validateDataWithRules()
    }


    private fun signUp() {
        Log.d(TAG, "Dentro_de_signUpn")
        printState()

        viewModelScope.launch {
            authUseCases.signUp(state.toSignUpRequest("")).collect {
                _signUpResponse.value = it
                when (it) {
                    is ApiResponse.Failure -> {
                        Log.d("NOPPERSSSSSS :/ ", "message : ${it.errorMessage}")
                    }

                    ApiResponse.Loading -> {}
                    is ApiResponse.Success -> {
                        val message = it.data.message
                        state.showDialog = true
                        authUseCases.signIn(username = state.username, password = state.password)
                        Log.d("REGISTRADO!!", "message : $message")
                    }

                    ApiResponse.Waiting -> {}
                }
            }
        }
    }

    private fun validateDataWithRules() {
        val firstNameResult = Validator.validateFirstName(state.firstName)
        val lastNameResult = Validator.validateLastName(state.lastName)
        val usernameResult = Validator.validateUsername(state.username)
        val emailResult = Validator.validateEmail(state.email)
        val phoneResult = Validator.validatePhone(state.phone)
        val passwordResult = Validator.validatePassword(state.password)
        val privacyPolicyResult = Validator.validatePrivacyPolicyAcceptance(
            statusValue = state.privacyPolicyAccepted
        )

        Log.d(TAG, "Dentro_de_validateDataWithRules")

        Log.d(TAG, "firstNameResult = ${firstNameResult}")
        Log.d(TAG, "lastNameResult = ${lastNameResult}")
        Log.d(TAG, "usernameResult = ${usernameResult}")
        Log.d(TAG, "emailNameResult = ${emailResult}")
        Log.d(TAG, "passwordResult = ${passwordResult}")
        Log.d(TAG, "privacyPolicyResult= $privacyPolicyResult")

        state = state.copy(
            firstNameError = firstNameResult.status,
            lastNameError = lastNameResult.status,
            usernameError = usernameResult.status,
            emailError = emailResult.status,
            phoneError = phoneResult.status,
            passwordError = passwordResult.status,
            privacyPolicyError = privacyPolicyResult.status
        )
        allValidationsPassed =
            firstNameResult.status &&
                    lastNameResult.status &&
                    emailResult.status &&
                    phoneResult.status &&
                    passwordResult.status &&
                    privacyPolicyResult.status &&
                    usernameResult.status

        Log.d(TAG, "allValidationsPassed= $allValidationsPassed")

    }

    private fun printState() {
        Log.d(TAG, "Dentro_de_printState")
        Log.d(TAG, state.toString())
    }
}