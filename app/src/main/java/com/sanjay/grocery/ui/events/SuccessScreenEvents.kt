package com.sanjay.grocery.ui.events

sealed class SuccessScreenEvents {
    data object OrderMoreClicked : SuccessScreenEvents()

    data object OnBackPressed : SuccessScreenEvents()
}