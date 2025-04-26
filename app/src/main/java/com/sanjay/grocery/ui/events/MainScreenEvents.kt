package com.sanjay.grocery.ui.events

sealed class MainScreenEvents {

    data object OnOrderNow : MainScreenEvents()

    data object OnDismiss : MainScreenEvents()

}