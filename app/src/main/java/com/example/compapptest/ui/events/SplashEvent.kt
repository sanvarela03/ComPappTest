package com.example.compapptest.ui.events

sealed class SplashEvent {
    object CheckAuthentication : SplashEvent()
}