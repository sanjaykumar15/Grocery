package com.sanjay.grocery.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentBody(
    val amount: String,
    val currency: String,
)