package com.example.compapptest.ui.states

import com.example.compapptest.data.local.entities.AddressEntity
import com.example.compapptest.data.local.entities.relations.ProducerAndAddress

data class ProducerSearchState(
    val producerList: List<ProducerAndAddress> = emptyList(),
    val isRefreshing: Boolean = false,
)
