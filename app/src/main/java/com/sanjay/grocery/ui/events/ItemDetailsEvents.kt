package com.sanjay.grocery.ui.events

sealed class ItemDetailsEvents {
    data object OnBackPressed : ItemDetailsEvents()
    data object OnFavClicked : ItemDetailsEvents()

    data class OnInitRefresh(
        val tyreId: Int,
        val typeName: String,
    ) : ItemDetailsEvents()

    data class ShowToast(val msg: String) : ItemDetailsEvents()
    data class OnAddToCart(
        val typeId: Int?,
        val typeName: String?,
    ) : ItemDetailsEvents()
}