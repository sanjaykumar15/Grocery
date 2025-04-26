package com.sanjay.grocery.models

import kotlinx.serialization.Serializable

@Serializable
data class CategoryListItem(
    var categoryID: Int? = null,
    var categoryType: String? = null,
    var categoryName: String? = null,
    var categoryImage: String? = null,
    var totalItems: Int? = null,
)