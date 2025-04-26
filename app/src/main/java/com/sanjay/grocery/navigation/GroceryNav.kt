package com.sanjay.grocery.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.sanjay.grocery.ui.events.CategoryItemsEvents
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.events.MainScreenEvents
import com.sanjay.grocery.ui.screens.CategoryItemsScreen
import com.sanjay.grocery.ui.screens.HomeScreen
import com.sanjay.grocery.ui.screens.MainScreen
import com.sanjay.grocery.ui.viewmodels.CategoryItemsVM
import com.sanjay.grocery.ui.viewmodels.HomeViewModel

@Composable
fun GroceryNav(
    navController: NavHostController,
    initialRoute: Any,
    onTerminate: () -> Unit,
    showToast: (String) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = initialRoute
    ) {
        composable<MainScreen> {
            MainScreen(
                onEvent = { event ->
                    when (event) {
                        is MainScreenEvents.OnOrderNow -> {
                            navController.navigate(HomeScreen)
                        }

                        is MainScreenEvents.OnDismiss -> {
                            onTerminate()
                        }
                    }
                }
            )
        }

        composable<HomeScreen> {
            val viewModel = hiltViewModel<HomeViewModel>()
            HomeScreen(
                state = viewModel.state,
                onEvent = { event ->
                    when (event) {
                        is HomeScreenEvents.OnBackPressed -> {
                            navController.navigateUp()
                        }

                        is HomeScreenEvents.ShowToast -> {
                            showToast(event.msg)
                            viewModel.eventHandler(event)
                        }

                        is HomeScreenEvents.OnCategorySelected -> {
                            navController.navigate(
                                CategoryItemsNav(
                                    type = event.type,
                                    title = event.name,
                                    tyreId = event.typeId
                                )
                            )
                        }

                        else -> {
                            viewModel.eventHandler(event)
                        }
                    }
                }
            )
        }

        composable<CategoryItemsNav> {
            val viewModel = hiltViewModel<CategoryItemsVM>()
            CategoryItemsScreen(
                state = viewModel.state,
                navItem = it.toRoute<CategoryItemsNav>(),
                onEvent = { event ->
                    when (event) {
                        is CategoryItemsEvents.OnBackPressed -> {
                            navController.navigateUp()
                        }

                        is CategoryItemsEvents.ShowToast -> {
                            showToast(event.msg)
                            viewModel.eventHandler(event)
                        }

                        else -> {
                            viewModel.eventHandler(event)
                        }
                    }
                }
            )
        }
    }
}