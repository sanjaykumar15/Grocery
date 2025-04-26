package com.sanjay.grocery.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sanjay.grocery.navigation.HomeScreenNav
import com.sanjay.grocery.ui.BottomBarItem
import com.sanjay.grocery.ui.components.ErrorView
import com.sanjay.grocery.ui.components.TopBarComp
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.states.HomeScreenState
import com.sanjay.grocery.ui.theme.Background
import com.sanjay.grocery.ui.theme.LightGrey

@Composable
fun HomeScreen(
    state: HomeScreenState,
    navItem: HomeScreenNav,
    onEvent: (HomeScreenEvents) -> Unit,
) {
    if (state.isInit) {
        onEvent(
            HomeScreenEvents.OnInitRefresh(
                isCart = navItem.isCart,
                typeId = navItem.typeId
            )
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Background,
        topBar = {
            TopBarComp(
                title = "",
                onBackClick = {
                    onEvent(HomeScreenEvents.OnBackPressed)
                }
            )
        },
        bottomBar = {
            BottomAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp
                        )
                    )
                    .border(
                        width = 1.dp,
                        color = LightGrey,
                        shape = RoundedCornerShape(
                            topStart = 30.dp,
                            topEnd = 30.dp
                        )
                    ),
                containerColor = Background
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    state.screens.forEach { screen ->
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clickable(onClick = {
                                    onEvent(HomeScreenEvents.OnBottomItemClick(screen))
                                }),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = if (state.selectedScreen == screen) screen.focusedIcon else screen.icon),
                                contentDescription = screen.title
                            )
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = Background
        ) {
            if (state.error.isNotEmpty() && state.showErrorView) {
                ErrorView(
                    errorMsg = state.error
                ) {
                    onEvent(HomeScreenEvents.OnRefresh)
                }
            } else {
                if (state.error.isNotEmpty()) {
                    onEvent(HomeScreenEvents.ShowToast(state.error))
                }
                if (state.selectedScreen == BottomBarItem.Home) {
                    CategoryScreenView(
                        listItems = if (state.searchList.isNotEmpty()) state.searchList
                        else state.categoryList,
                        onEvent = onEvent,
                        isRefreshing = state.isLoading
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        state = HomeScreenState(),
        navItem = HomeScreenNav(),
        onEvent = {}
    )
}