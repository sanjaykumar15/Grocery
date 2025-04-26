package com.sanjay.grocery.ui

import com.sanjay.grocery.R

sealed class BottomBarItem(
    val title: String,
    val icon: Int,
    val focusedIcon: Int,
) {
    object Home : BottomBarItem(
        title = "Home",
        icon = R.drawable.ic_home,
        focusedIcon = R.drawable.ic_home_focused
    )

    object Cart : BottomBarItem(
        title = "Cart",
        icon = R.drawable.ic_cart,
        focusedIcon = R.drawable.ic_cart_focused
    )

    object Profile : BottomBarItem(
        title = "Profile",
        icon = R.drawable.ic_profile,
        focusedIcon = R.drawable.ic_profile_focused
    )
}