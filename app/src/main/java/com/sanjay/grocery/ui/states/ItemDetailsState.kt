package com.sanjay.grocery.ui.states

import com.sanjay.grocery.models.CategoryItems

data class ItemDetailsState(
    val isInit: Boolean = true,
    val error: String? = null,
    val selectedTypeID: Int? = null,
    val selectedTypeName: String? = null,
    val item: CategoryItems? = null,
    val isFavorite: Boolean = false,
)
