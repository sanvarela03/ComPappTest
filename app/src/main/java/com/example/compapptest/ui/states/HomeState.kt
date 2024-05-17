package com.example.compapptest.ui.states

import com.example.compapptest.data.remote.payload.response.customer.CustomerInfoResponse

data class HomeState(
    var customerInfoResponse: CustomerInfoResponse? = null,
    val isRefreshing: Boolean = false,
)
