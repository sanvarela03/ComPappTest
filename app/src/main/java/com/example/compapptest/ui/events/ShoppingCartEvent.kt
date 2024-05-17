package com.example.compapptest.ui.events

sealed class ShoppingCartEvent {
    data class DeleteItem(val id: Long) : ShoppingCartEvent()
}

