package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.grocery.core.Constants
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.network.ApiService
import com.sanjay.grocery.ui.BottomBarItem
import com.sanjay.grocery.ui.events.CartEvents
import com.sanjay.grocery.ui.events.HomeScreenEvents
import com.sanjay.grocery.ui.states.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    var apiService: ApiService,
    var realmHelper: RealmHelper,
) : ViewModel() {

    var state by mutableStateOf(HomeScreenState())
        private set

    fun eventHandler(event: HomeScreenEvents) {
        when (event) {
            is HomeScreenEvents.OnInitRefresh -> {
                if (event.isCart) {
                    state = state.copy(
                        selectedScreen = BottomBarItem.Cart,
                        selectedTypeId = event.typeId,
                        selectedTyreName = event.typeName
                    )
                }
                val categories = realmHelper.getCategoryList()
                if (categories.isEmpty()) {
                    getCategories()
                } else {
                    state = state.copy(
                        categoryList = categories,
                        isLoading = false,
                        showErrorView = false,
                        error = "",
                        isInit = false
                    )
                }
            }

            is HomeScreenEvents.OnRefresh -> {
                getCategories()
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

    fun cartEventHandler(event: CartEvents) {
        when (event) {
            is CartEvents.OnInitRefresh -> {
                state = state.copy(
                    paymentData = realmHelper.getPaymentData(),
                    isCartInit = false
                )
            }

            is CartEvents.OnChangeClicked -> {
                when (event.changeFor) {
                    Constants.CHANGE_FOR_ADDRESS -> {

                    }

                    Constants.CHANGE_FOR_DELIVERY -> {

                    }
                }
            }

            is CartEvents.OnDeliveryOptionClicked -> {
                state = state.copy(
                    paymentData = state.paymentData.copy(
                        deliveryMethod = event.option
                    )
                )
            }

            else -> {}
        }
    }

    fun getCategories() {
        state = state.copy(
            isLoading = true,
            showErrorView = false,
            error = ""
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = apiService.getCategories()
                state = if (result.success) {
                    state.copy(
                        categoryList = realmHelper.getCategoryList(),
                        isLoading = false,
                        showErrorView = false,
                        error = "",
                        isInit = false
                    )
                } else {
                    state.copy(
                        categoryList = realmHelper.getCategoryList(),
                        isLoading = false,
                        showErrorView = false,
                        error = result.error,
                        isInit = false
                    )
                }
            } catch (e: Exception) {
                state = state.copy(
                    isLoading = false,
                    showErrorView = true,
                    error = e.message.toString(),
                    isInit = false
                )
            }
        }
    }

}