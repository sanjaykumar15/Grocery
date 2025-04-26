package com.sanjay.grocery.ui.states

import com.sanjay.grocery.models.CategoryItems

data class CardDetailsState(
    val isInit: Boolean = true,
    val cardNumber: String = "",
    val cardHolderName: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val categoryItem: CategoryItems? = null,
)
