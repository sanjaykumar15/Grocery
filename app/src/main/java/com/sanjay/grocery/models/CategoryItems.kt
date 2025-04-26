package com.sanjay.grocery.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryItems(
    val typeID: Int? = null,
    val typeName: String? = null,
    val description: String? = null,
    val thumbnailImage: String? = null,
    val sliderImages: List<String> = emptyList(),
    val pricePerPiece: Float? = null,
    val weightPerPiece: Int? = null,
    val isFav: Boolean = false,
)
