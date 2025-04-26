package com.sanjay.grocery.ui.events

import com.sanjay.grocery.ui.BottomBarItem

sealed class HomeScreenEvents {

    data object OnBackPressed : HomeScreenEvents()

    data class OnRefresh(val refresh: Boolean) : HomeScreenEvents()
    data class OnBottomItemClick(val item: BottomBarItem) : HomeScreenEvents()
    data class ShowToast(val msg: String) : HomeScreenEvents()
    data class OnSearch(val query: String) : HomeScreenEvents()
    data class OnCategorySelected(val type: String) : HomeScreenEvents()

}