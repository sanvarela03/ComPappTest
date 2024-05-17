package com.example.compapptest.ui.states

import com.example.compapptest.data.local.entities.ShoppingCartEntity

data class ShoppingCartState(
    val items: List<ShoppingCartEntity> = emptyList()
)
