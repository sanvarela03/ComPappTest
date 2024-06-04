package com.example.compapptest.ui.states

import com.example.compapptest.data.local.entities.ProducerEntity
import com.example.compapptest.data.local.entities.ProductEntity
import com.example.compapptest.data.local.entities.ResourceEntity

data class ProducerDetailState(
    val producer: ProducerEntity? = null,
    val currentProducerId: Long = -1L,
    val resource: ResourceEntity? = null,
    val productsList: List<ProductEntity> = emptyList(),
    val isRefreshing: Boolean = false,
)
