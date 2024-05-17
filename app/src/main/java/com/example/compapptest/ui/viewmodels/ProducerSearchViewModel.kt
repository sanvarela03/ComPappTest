package com.example.compapptest.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compapptest.config.common.ApiResponse
import com.example.compapptest.domain.use_cases.producer.ProducerUseCases
import com.example.compapptest.ui.events.ProducerSearchEvent
import com.example.compapptest.ui.states.AddressListState
import com.example.compapptest.ui.states.ProducerSearchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProducerSearchViewModel @Inject constructor(
    private val producerUseCases: ProducerUseCases
) : ViewModel() {

    var state by mutableStateOf(ProducerSearchState())

    init {
        searchProducers(fetchFromRemote = true)
    }

    fun onEvent(event: ProducerSearchEvent) {
        when (event) {
            is ProducerSearchEvent.LookForProductsBtnClick -> {}
            ProducerSearchEvent.Refresh -> {
                searchProducers(fetchFromRemote = true)
            }
        }
    }

    private fun searchProducers(fetchFromRemote: Boolean = false) {
        viewModelScope.launch {
            producerUseCases.searchProducers(fetchFromRemote).collect {
                when (it) {
                    ApiResponse.Waiting -> {}
                    ApiResponse.Loading -> {}
                    is ApiResponse.Failure -> {}
                    is ApiResponse.Success -> {
                        state = state.copy(producerList = it.data)
                    }
                }
            }
        }
    }

}