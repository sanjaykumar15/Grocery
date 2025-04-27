package com.sanjay.grocery.core

import com.sanjay.grocery.models.PaymentResponse

data class ApiResults(
    var success: Boolean,
    var error: String = "",
    var paymentResponse: PaymentResponse? = null,
)
