package com.sanjay.grocery.ui.states

import com.sanjay.grocery.models.CategoryItems
import com.sanjay.grocery.models.PaymentResponse

data class CardDetailsState(
    val isInit: Boolean = true,
    val isLoading: Boolean = false,
    val error: String = "",
    val cardNumber: String = "",
    val cardHolderName: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val categoryItem: CategoryItems? = null,
    val isApiKeySuccess: Boolean = false,
    val paymentResponse: PaymentResponse? = null,
    val showAlert: Boolean = false,
)
