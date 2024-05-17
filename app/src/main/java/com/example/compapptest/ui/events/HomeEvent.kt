package com.example.compapptest.ui.events

import kotlin.coroutines.CoroutineContext

sealed class HomeEvent {
    data class SignOutBtnClicked(val context: CoroutineContext) : HomeEvent()
    object Refresh : HomeEvent()
}