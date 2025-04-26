package com.sanjay.grocery.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreen

@Serializable
data class HomeScreenNav(
    val isCart: Boolean = false,
    val tyreId: Int? = null,
)

@Serializable
data class CategoryItemsNav(
    val type: String,
    val title: String,
    val tyreId: Int,
)

@Serializable
data class ItemDetailsNav(
    val type: String,
    val tyreId: Int,
)