package com.sanjay.grocery.ui.events

sealed class CartEvents {
    data object OnInitRefresh : CartEvents()
    data object OnPaymentInit : CartEvents()

    data class OnChangeClicked(
        val changeFor: String,
    ) : CartEvents()

    data class OnDeliveryOptionClicked(
        val option: String,
    ) : CartEvents()
}