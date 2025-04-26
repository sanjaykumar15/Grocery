package com.sanjay.grocery.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryListItem(
    val categoryID: Int? = null,
    val categoryType: String? = null,
    val categoryName: String? = null,
    val categoryImage: String? = null,
    val totalItems: Int? = null,
)