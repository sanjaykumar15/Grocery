package com.sanjay.grocery.ui.events

import com.sanjay.grocery.ui.BottomBarItem

sealed class HomeScreenEvents {

    data object OnBackPressed : HomeScreenEvents()
    data object OnRefresh : HomeScreenEvents()

    data class OnInitRefresh(
        val isCart: Boolean,
        val typeId: Int?,
    ) : HomeScreenEvents()

    data class OnBottomItemClick(val item: BottomBarItem) : HomeScreenEvents()
    data class ShowToast(val msg: String) : HomeScreenEvents()
    data class OnSearch(val query: String) : HomeScreenEvents()
    data class OnCategorySelected(
        val type: String,
        val name: String,
        val typeId: Int,
    ) : HomeScreenEvents()

}