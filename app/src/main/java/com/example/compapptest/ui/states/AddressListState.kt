package com.example.compapptest.ui.states

import com.example.compapptest.data.local.entities.AddressEntity

data class AddressListState(
    val addressList: List<AddressEntity> = emptyList(),
    val isRefreshing: Boolean = false,
)
