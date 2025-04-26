package com.sanjay.grocery.ui.events

sealed class CategoryItemsEvents {
    data object OnBackPressed : CategoryItemsEvents()
    data object OnRefresh : CategoryItemsEvents()

    data class OnInitRefresh(
        val type: String,
        val title: String,
        val tyreId: Int,
    ) : CategoryItemsEvents()

    data class ShowToast(val msg: String) : CategoryItemsEvents()
    data class OnSearch(val query: String) : CategoryItemsEvents()
    data class OnCategoryItemClicked(
        val typeId: Int,
        val typeName: String,
    ) : CategoryItemsEvents()
}