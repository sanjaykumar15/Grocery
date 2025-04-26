package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.ui.events.ItemDetailsEvents
import com.sanjay.grocery.ui.states.ItemDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailsVM @Inject constructor(
    var realmHelper: RealmHelper,
) : ViewModel() {

    var state by mutableStateOf(ItemDetailsState())
        private set

    fun eventHandler(event: ItemDetailsEvents) {
        when (event) {
            is ItemDetailsEvents.OnInitRefresh -> {
                val item = realmHelper.getCategoryItem(event.tyreId, event.typeName)
                if (item == null) {
                    state = state.copy(error = "Item not found")
                    return
                }
                state = state.copy(
                    selectedTypeID = event.tyreId,
                    selectedTypeName = event.typeName,
                    item = item,
                    isFavorite = item.isFav
                )
            }

            is ItemDetailsEvents.OnFavClicked -> {
                state = state.copy(isFavorite = !state.isFavorite)
                updateFav()
            }

            is ItemDetailsEvents.ShowToast -> {
                state = state.copy(error = null)
            }

            else -> {}
        }
    }

    private fun updateFav() {
        viewModelScope.launch(Dispatchers.IO) {
            realmHelper.updateCategoryItem(state.selectedTypeID, state.selectedTypeName)
        }
    }

}