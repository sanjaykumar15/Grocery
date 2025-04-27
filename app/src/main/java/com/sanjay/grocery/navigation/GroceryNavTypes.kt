package com.sanjay.grocery.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreen

@Serializable
data class HomeScreenNav(
    val isCart: Boolean = false,
    val typeId: Int? = null,
    val typeName: String? = null,
)

@Serializable
data class CategoryItemsNav(
    val type: String,
    val title: String,
    val typeId: Int,
)

@Serializable
data class ItemDetailsNav(
    val type: String,
    val typeId: Int,
)

@Serializable
data class CardDetailsNav(
    val typeId: Int?,
    val typeName: String?,
    val cardNumber: String?,
)

@Serializable
data object SuccessScreenNav