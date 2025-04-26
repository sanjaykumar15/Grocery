package com.sanjay.grocery.models

data class PaymentData(
    val name: String = "",
    val cardNumber: String = "",
    val expiryDate: String = "",
    val cvv: String = "",
    val address: String = "",
    val deliveryMethod: String = "",
)