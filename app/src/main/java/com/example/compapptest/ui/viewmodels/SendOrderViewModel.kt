package com.example.compapptest.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.local.entities.relations.ProducerAndShoppingCart
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.use_cases.address.AddressUseCases
import com.example.compapptest.domain.use_cases.order.OrderUseCases
import com.example.compapptest.domain.use_cases.shoping_cart.ShoppingCartUseCases
import com.example.compapptest.ui.events.SendOrderEvent
import com.example.compapptest.ui.states.ProducerDetailState
import com.example.compapptest.ui.states.SendOrderState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendOrderViewModel @Inject constructor(
    private val orderUseCases: OrderUseCases,
    private val shoppingCartUseCases: ShoppingCartUseCases,
    private val addressUseCases: AddressUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var state by mutableStateOf(SendOrderState())

    lateinit var producerAndShoppingCart: Flow<ProducerAndShoppingCart?>

    private val _apiFlow = MutableStateFlow<ApiResponse<MessageResponse>>(ApiResponse.Waiting)
    val apiFlow = _apiFlow.asStateFlow()

    var showDialog by mutableStateOf(false)

    init {
        val producerId = savedStateHandle.get<Long>("producerId")
        producerId?.let { producerId ->
            if (producerId != -1L) {
                producerAndShoppingCart =
                    shoppingCartUseCases.getProducerAndShoppingCart(producerId)

                getAllAddress()
            }
        }
    }


    fun onEvent(event: SendOrderEvent) {
        when (event) {
            is SendOrderEvent.AddPaymentMethod -> {}
            is SendOrderEvent.SelectDeliveryAddressId -> {}
            SendOrderEvent.SendOrder -> {

                Log.d("SIP", "state : ${state.toAddOrderRequest()}")
                addOrder()
            }
        }
    }

    private fun addOrder() {
        viewModelScope.launch {
            orderUseCases.addOrder(state.toAddOrderRequest()).collect {
                _apiFlow.value = it
            }
        }
    }

    private fun getAllAddress(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            addressUseCases.getAllAddresses(fetchFromRemote).collect {
                when (it) {
                    ApiResponse.Waiting -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Failure -> {}
                    is ApiResponse.Success -> {
                        state = state.copy(addressList = it.data)
                    }
                }
            }
        }
    }

}