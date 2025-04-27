package com.sanjay.grocery.models

import kotlinx.serialization.Serializable

@Serializable
data class PaymentResponse(
    val paymentIntent: String,
    val ephemeralKey: String,
    val customer: String,
    val publishableKey: String,
)
