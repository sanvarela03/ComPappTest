package com.example.compapptest.ui.events

import com.example.compapptest.data.local.entities.ShoppingCartEntity

sealed class ProducerDetailEvent {
    data class AddProductToCart(val shoppingCartEntity: ShoppingCartEntity) : ProducerDetailEvent()
    object Refresh : ProducerDetailEvent()
}