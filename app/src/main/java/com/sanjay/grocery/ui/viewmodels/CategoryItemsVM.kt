package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.network.ApiService
import com.sanjay.grocery.ui.events.CategoryItemsEvents
import com.sanjay.grocery.ui.states.CategoryItemsState
import com.sanjay.grocery.util.exceptionUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryItemsVM @Inject constructor(
    var apiService: ApiService,
    var realmHelper: RealmHelper,
) : ViewModel() {

    var state by mutableStateOf(CategoryItemsState())
        private set

    fun eventHandler(event: CategoryItemsEvents) {
        when (event) {
            is CategoryItemsEvents.OnInitRefresh -> {
                state = state.copy(
                    selectedType = event.type,
                    selectedTypeID = event.typeId,
                    title = event.title,
                    isInit = false
                )
                getCategoryItems(event.type)
            }

            is CategoryItemsEvents.OnRefresh -> {
                getCategoryItems(state.selectedType)
            }

            is CategoryItemsEvents.ShowToast -> {
                state = state.copy(error = "")
            }

            is CategoryItemsEvents.OnSearch -> {
                val filteredRes = state.categoryItems.filter {
                    it.typeName?.contains(event.query, true) == true
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

    private fun getCategoryItems(type: String) {
        state = state.copy(
            isLoading = true,
            showErrorView = false,
            error = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val results = apiService.getCategoryItems(type)
                state = state.copy(
                    categoryItems = realmHelper.getCategoryThumbnailItems(state.selectedTypeID),
                    isLoading = false,
                    error = if (results.success) "" else results.error,
                    showErrorView = false
                )
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    showErrorView = true,
                    error = exceptionUtil(e)
                )
            }
        }
    }

}