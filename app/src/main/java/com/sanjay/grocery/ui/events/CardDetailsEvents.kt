package com.sanjay.grocery.ui.events

sealed class CardDetailsEvents {
    data object OnBackPressed : CardDetailsEvents()
    data object Submit : CardDetailsEvents()
    data object HideAlert : CardDetailsEvents()

    data class OnInit(
        val typeId: Int?,
        val typeName: String?,
        val cardNo: String?,
    ) : CardDetailsEvents()

    data class OnCardNoChange(
        val number: String,
    ) : CardDetailsEvents()

    data class OnCardHolderNameChange(
        val name: String,
    ) : CardDetailsEvents()

    data class OnExpiryDateChange(
        val date: String,
    ) : CardDetailsEvents()

    data class OnCvvChange(
        val cvv: String,
    ) : CardDetailsEvents()

    data class ShowToast(
        val msg: String,
    ) : CardDetailsEvents()

    data class OnPaymentResult(
        val isSuccess: Boolean,
        val msg: String,
    ) : CardDetailsEvents()
}