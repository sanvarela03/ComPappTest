package com.example.compapptest.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.data.remote.payload.response.customer.address.AddressResponse
import com.example.compapptest.domain.use_cases.address.AddressUseCases
import com.example.compapptest.ui.events.AddressListEvent
import com.example.compapptest.ui.states.AddressListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressListViewModel @Inject constructor(
    private val addressUseCases: AddressUseCases,
) : ViewModel() {

    private val _apiFlow = MutableStateFlow<ApiResponse<MessageResponse>>(ApiResponse.Waiting)
    val apiFlow = _apiFlow.asStateFlow()

    var state by mutableStateOf(AddressListState())


    var showDialog by mutableStateOf(false)
    var id by mutableStateOf(-1L)
    var currentAddress by mutableStateOf<AddressResponse?>(null)

    init {
        getAllAddress()
    }


    fun onEvent(event: AddressListEvent) {
        when (event) {
            is AddressListEvent.DeleteBtnClick -> {
                deleteAddress(event.id)
            }

            AddressListEvent.Refresh -> {
                getAllAddress(fetchFromRemote = true)
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

    private fun deleteAddress(id: Long) {
        viewModelScope.launch {
            addressUseCases.deleteAddress(id).collect {
                _apiFlow.value = it
            }
        }
    }
}