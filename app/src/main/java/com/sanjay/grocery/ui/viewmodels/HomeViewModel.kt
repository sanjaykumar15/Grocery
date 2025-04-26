package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.states.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var realm: Realm,
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())
        private set

    fun eventHandler(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnRefresh -> {

            }

            is HomeScreenEvents.OnBottomItemClick -> {
                state = state.copy(
                    selectedScreen = event.item
                )
            }

            is HomeScreenEvents.ShowToast -> {
                state = state.copy(error = "")
            }

            is HomeScreenEvents.OnSearch -> {
                val filteredRes = state.categoryList.filter {
                    it.categoryName?.contains(event.query, true) == true
                }
                state = if (event.query.isNotEmpty()) {
                    state.copy(searchList = filteredRes)
                } else {
                    state.copy(searchList = emptyList())
                }
            }

            else -> {}
        }
    }

}