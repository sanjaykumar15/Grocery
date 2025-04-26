package com.sanjay.grocery.ui.states

import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.ui.BottomBarItem

data class HomeScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    val showErrorView: Boolean = false,
    val categoryList: List<CategoryListItem> = emptyList(),
    val searchList: List<CategoryListItem> = emptyList(),
    val screens: List<BottomBarItem> = listOf(
        BottomBarItem.Home,
        BottomBarItem.Cart,
        BottomBarItem.Profile
    ),
    val selectedScreen: BottomBarItem = BottomBarItem.Home,
)
