package com.sanjay.grocery.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.events.MainScreenEvents
import com.sanjay.grocery.ui.screens.HomeScreen
import com.sanjay.grocery.ui.screens.MainScreen
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

                        else -> {
                            viewModel.eventHandler(event)
                        }
                    }
                }
            )
        }
    }
}