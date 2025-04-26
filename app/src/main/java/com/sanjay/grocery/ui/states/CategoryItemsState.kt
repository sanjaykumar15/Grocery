package com.sanjay.grocery.ui.states

import com.sanjay.grocery.models.CategoryItems

data class CategoryItemsState(
    val isInit: Boolean = true,
    val selectedType: String = "",
    val title: String = "",
    val selectedTypeID: Int? = null,
    val isLoading: Boolean = false,
    val error: String = "",
    val showErrorView: Boolean = false,
    val categoryItems: List<CategoryItems> = emptyList(),
    val searchList: List<CategoryItems> = emptyList(),
)