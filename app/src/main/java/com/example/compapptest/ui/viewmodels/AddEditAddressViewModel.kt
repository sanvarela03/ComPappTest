package com.example.compapptest.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.data.remote.payload.response.MessageResponse
import com.example.compapptest.domain.use_cases.address.AddressUseCases
import com.example.compapptest.ui.events.AddEditAddressEvent
import com.example.compapptest.ui.states.AddEditAddressState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AddEditAddressViewModel @Inject constructor(
    private val addressUseCases: AddressUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var isAttemptingToSave by mutableStateOf(true)

    private var currentAddressId: Long? = null

    var state by mutableStateOf(AddEditAddressState())

    private val _apiFlow = MutableStateFlow<ApiResponse<MessageResponse>>(ApiResponse.Waiting)
    val apiFlow = _apiFlow.asStateFlow()


    var showDialog by mutableStateOf(false)

    init {

        val addressId = savedStateHandle.get<Long>("addressId")

        addressId?.let { addressId ->
            if (addressId != -1L) {
                isAttemptingToSave = false

                viewModelScope.launch {
                    addressUseCases.getAddress(addressId)?.also { address ->
                        currentAddressId = address.id
                        state = state.copy(
                            name = address.name,
                            country = address.country,
                            state = address.state,
                            city = address.city,
                            street = address.street,
                            instruction = address.instruction,
                            latitude = address.latitude.toString(),
                            longitude = address.longitude.toString(),
                        )

                    }
                }

            } else {
                isAttemptingToSave = true
            }
        }
    }

    fun onEvent(event: AddEditAddressEvent) {
        when (event) {
            is AddEditAddressEvent.NameChanged -> {
                state = state.copy(name = event.name)
            }

            is AddEditAddressEvent.InstructionChanged -> {
                state = state.copy(instruction = event.instruction)
            }

            is AddEditAddressEvent.StreetChanged -> {
                state = state.copy(street = event.street)
            }

            is AddEditAddressEvent.CityChanged -> {
                state = state.copy(city = event.city)
            }

            is AddEditAddressEvent.StateChanged -> {
                state = state.copy(state = event.state)
            }

            is AddEditAddressEvent.CountryChanged -> {
                state = state.copy(country = event.country)
            }

            is AddEditAddressEvent.LatitudeChanged -> {
                state = state.copy(latitude = event.latitude)
            }

            is AddEditAddressEvent.LongitudeChanged -> {
                state = state.copy(longitude = event.longitude)
            }

            is AddEditAddressEvent.IsCurrentAddressChanged -> {
                state = state.copy(isCurrentAddress = event.isCurrentAddress)
            }

            AddEditAddressEvent.SaveBtnClick -> {
                if (isAttemptingToSave) {
                    saveAddress()
                } else {
                    updateAddress()
                }
            }
        }
    }

    private fun saveAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            addressUseCases.addAddress(state.toAddressRequest()).collect {
                withContext(Dispatchers.Main) {
                    _apiFlow.value = it
                }
            }
        }
    }

    private fun updateAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            if (currentAddressId != null) {
                addressUseCases.updateAddress(state.toUpdateAddressRequest(currentAddressId!!))
                    .collect {
                        _apiFlow.value = it
                    }
            }
        }
    }
}