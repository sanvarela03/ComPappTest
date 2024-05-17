package com.example.compapptest.ui.events

sealed class ProducerSearchEvent {
    data class LookForProductsBtnClick(val id: Long) : ProducerSearchEvent()
    object Refresh : ProducerSearchEvent()
}

