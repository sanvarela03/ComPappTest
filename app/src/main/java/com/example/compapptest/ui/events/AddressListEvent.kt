package com.example.compapptest.ui.events

sealed class AddressListEvent {
    data class DeleteBtnClick(val id: Long) : AddressListEvent()
    object Refresh : AddressListEvent()
}