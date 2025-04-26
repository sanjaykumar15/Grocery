package com.sanjay.grocery.navigation

import kotlinx.serialization.Serializable

@Serializable
data object MainScreen

@Serializable
data object HomeScreen

@Serializable
data class CategoryItemsNav(
    val type: String,
    val title: String,
    val tyreId: Int,
)