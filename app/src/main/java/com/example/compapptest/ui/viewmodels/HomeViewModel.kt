package com.example.compapptest.ui.viewmodels

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.dto.NavigationItem
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.use_cases.customer.CustomerUseCases
import com.example.compapptest.ui.events.HomeEvent
import com.example.compapptest.ui.events.SplashEvent
import com.example.compapptest.ui.navigation.Screen
import com.example.compapptest.ui.navigation.graphs.Graph
import com.example.compapptest.ui.states.HomeState

import com.example.protapptest.domain.use_cases.auth.AuthUseCases
import com.example.protapptest.security.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authUseCases: AuthUseCases,
    private val customerUseCases: CustomerUseCases,
    private val tokenManager: TokenManager,
    private val authViewModel: AuthViewModel
) : ViewModel() {

    var state by mutableStateOf(HomeState())

    private val _signOutResponse: MutableState<ApiResponse<MessageResponse>> =
        mutableStateOf(ApiResponse.Loading)
    val signOutResponse = _signOutResponse


    var signOutJob: Job? = null
    var getCustomerJob: Job? = null

    init {
        Log.d("HomeViewModel", " init ")
        loadCustomer()
    }

    val bottomNavigationItemsList = listOf(
        NavigationItem(
            title = "Inicio",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = Screen.ProducerSearchScreen.route,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavigationItem(
            title = "Direcciones",
            icon = Icons.Default.Map,
            description = "Mis direcciones",
            itemId = Screen.AddressListScreen.route,
            selectedIcon = Icons.Filled.Map,
            unselectedIcon = Icons.Outlined.Map
        ),
        NavigationItem(
            title = "Carrito",
            icon = Icons.Default.ShoppingCart,
            description = "Mis direcciones",
            itemId = Screen.ShoppingCartScreen.route, // TODO
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        ),

        )

    val navigationItemsList = listOf(
        NavigationItem(
            title = "Inicio",
            icon = Icons.Default.Home,
            description = "Home Screen",
            itemId = "homeScreen"
        ),
        NavigationItem(
            title = "Mis pedidos",
            icon = Icons.Default.Person,
            description = "Favorite Screen",
            itemId = "ordersScreen"
        ),

        NavigationItem(
            title = "Direcciones",
            icon = Icons.Default.Map,
            description = "Favorite Screen",
            itemId = "addressScreen"
        ),
        NavigationItem(
            title = "Configuracion",
            icon = Icons.Default.Settings,
            description = "Settings Screen",
            itemId = "settingsScreen"
        ),
    )


    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.SignOutBtnClicked -> {
                signOut(event.context)
            }

            HomeEvent.Refresh -> {
                loadCustomer(fetchFromRemote = true)
            }
        }
    }

    private fun signOut(context: CoroutineContext) {
        Log.d(this.javaClass.simpleName, "signOutTest")
        signOutJob?.cancel()

        signOutJob = CoroutineScope(context).launch(Dispatchers.IO) {
            authUseCases.signOut().collect {
                Log.d(this.javaClass.simpleName, "authUseCases.signOut().collect{}")
                when (it) {
                    ApiResponse.Waiting -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Failure -> {
                        val data = it.errorMessage
                        Log.d("ApiResponse", "errorMessage = $data")
                    }

                    is ApiResponse.Success -> {
                        it.data.let {
                            Log.d("ApiResponse", "message = $it")
                            tokenManager.deleteAccessToken()
                            authViewModel.onEvent(SplashEvent.CheckAuthentication)
                        }
                    }

                }
            }
        }
    }

    private fun loadCustomer(fetchFromRemote: Boolean = false) {
        getCustomerJob?.cancel()
        getCustomerJob = viewModelScope.launch(Dispatchers.IO) {
            Log.d("HomeViewModel", "getProducer")

            val userId = tokenManager.getUserId().first()

            Log.d("HomeViewModel", "userId = $userId")
            if (userId != null) {
                customerUseCases.getCustomer(fetchFromRemote, userId).collect { apiResponse ->
                    when (apiResponse) {
                        ApiResponse.Waiting -> {}

                        ApiResponse.Loading -> {}

                        is ApiResponse.Failure -> {

                        }

                        is ApiResponse.Success -> {
                            apiResponse.data.let {
                                withContext(Dispatchers.Main) {
                                    state = state.copy(
                                        customerInfoResponse = it
                                    )
                                }
                            }

                            Log.d(
                                "HomeViewModel",
                                "getCustomer | Success | state.value = ${state} "
                            )
                        }

                    }
                }
            }
        }
    }

    override fun onCleared() {
        Log.d(this.javaClass.simpleName, "onCleared")
        super.onCleared()
        signOutJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }

        getCustomerJob?.let {
            if (it.isActive) {
                it.cancel()
            }
        }
    }

}