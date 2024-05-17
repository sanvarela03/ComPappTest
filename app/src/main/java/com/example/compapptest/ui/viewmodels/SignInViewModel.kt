package com.example.compapptest.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.protapptest.domain.use_cases.auth.AuthUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.SignInResponse
import com.example.compapptest.ui.events.SignInEvent
import com.example.compapptest.ui.events.SplashEvent
import com.example.compapptest.ui.rules.Validator
import com.example.compapptest.ui.states.SignInState
import com.example.protapptest.security.TokenManager
import kotlinx.coroutines.CoroutineScope

@HiltViewModel
class SignInViewModel
@Inject constructor(
    private val authUseCases: AuthUseCases,
    private val tokenManager: TokenManager,
    private val authViewModel: AuthViewModel,
//    private val viewModelScope: CoroutineScope
) : ViewModel() {

    private val _signInResponse: MutableState<ApiResponse<SignInResponse>> =
        mutableStateOf(ApiResponse.Loading)
    val signInResponse: State<ApiResponse<SignInResponse>> = _signInResponse


    private var signInJob: Job? = null

    var response by mutableStateOf<SignInResponse?>(null)

    private val _signInResponseTest: MutableState<ApiResponse<SignInResponse>> =
        mutableStateOf(ApiResponse.Loading)
    val signInResponseTest: State<ApiResponse<SignInResponse>> = _signInResponseTest


    var state by mutableStateOf(SignInState())
    var allValidationsPassed by mutableStateOf(false)

    fun onEvent(event: SignInEvent) {

        Log.d("SignInViewModel", "onEvent")
        when (event) {
            is SignInEvent.UsernameChanged -> {
                state = state.copy(username = event.username)
            }

            is SignInEvent.PasswordChanged -> {
                state = state.copy(password = event.password)
            }

            is SignInEvent.LoginButtonClicked -> {
                signInTest()
//                Log.d("SignInViewModel", "onEvent | LoginButtonClicked() ")
//                Log.d("SignInViewModel", "onEvent | signInJob = $signInJob ")
//                Log.d("SignInViewModel", "onEvent | viewModelScope = $viewModelScope.")
//                Log.d("SignInViewModel", "onEvent | viewModelScope = ${viewModelScope.launch { }}")



//                signInJob = viewModelScope.launch(
//                    Dispatchers.IO + CoroutineExceptionHandler { _, error ->
//                        viewModelScope.launch(Dispatchers.Main) {
//                            Log.d("SignInViewModel", "Ocurrió un error: ${error.localizedMessage}")
//                        }
//                    }
//                ) {
//                    signInTest()
//                }
            }
        }
        validateSignInUIDataWithRules()
    }

    private fun validateSignInUIDataWithRules() {
        val usernameResult = Validator.validateUsername(
            username = state.username
        )

        val passwordResult = Validator.validatePassword(
            password = state.password
        )

        state = state.copy(
            usernameError = usernameResult.status,
            passwordError = passwordResult.status
        )

        allValidationsPassed = usernameResult.status && passwordResult.status
    }

//    private fun signIn() =
//        baseRequest(
//            mutableStateFlow = _signInResponse,
//            errorHandler = object : CoroutinesErrorHandler {
//                override fun onError(message: String) {
//                    Log.d("ERROR AL SOLICITAR: ", message)
//                }
//            }
//        ) {
//            authUseCases.signIn(state.username, state.password)
//        }

    private fun signInTest() {
        Log.d("SignInViewModel", "onEvent | LoginButtonClicked() | signInTest()")

        signInJob?.cancel()
        signInJob = CoroutineScope(Dispatchers.Main + CoroutineExceptionHandler { _, error ->
            viewModelScope.launch(Dispatchers.Main) {
                Log.d("SignInViewModel", "Ocurrió un error: ${error.localizedMessage}")
            }
        }
        ).launch {


            Log.d(
                "SignInViewModel",
                "onEvent | LoginButtonClicked() | signInTest() | viewModelScope.launch  "
            )
            authUseCases.signIn(state.username, state.password)
                .collect {
                    when (it) {
                        is ApiResponse.Success -> {
                            it.data?.let {
                                tokenManager.saveAccessToken(it.token)
                                response = it
                                tokenManager.saveRefreshToken(it.refreshToken)
                                tokenManager.saveUserId(it.id)
                                authViewModel.onEvent(SplashEvent.CheckAuthentication)
                            }
                        }

                        is ApiResponse.Failure -> {}
                        is ApiResponse.Loading -> {}
                        ApiResponse.Waiting -> {}
                    }


                }
        }
    }

    override fun onCleared() {
        Log.d("SignInViewModel", "onCleared")
        super.onCleared()

        signInJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }
}