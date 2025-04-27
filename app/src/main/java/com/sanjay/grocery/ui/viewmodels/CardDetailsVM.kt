package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.models.PaymentBody
import com.sanjay.grocery.models.RPaymentData
import com.sanjay.grocery.network.ApiService
import com.sanjay.grocery.ui.events.CardDetailsEvents
import com.sanjay.grocery.ui.states.CardDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsVM @Inject constructor(
    val apiService: ApiService,
    var realmHelper: RealmHelper,
) : ViewModel() {

    var state by mutableStateOf(CardDetailsState())
        private set

    fun eventHandler(event: CardDetailsEvents) {
        when (event) {
            is CardDetailsEvents.OnInit -> {
                val paymentData = realmHelper.getPaymentDataByCardNo(event.cardNo)
                state = state.copy(
                    categoryItem = if (event.typeId != null && !event.typeName.isNullOrEmpty())
                        realmHelper.getCategoryItem(
                            typeId = event.typeId,
                            typeName = event.typeName
                        )
                    else null,
                    cardNumber = paymentData.cardNumber,
                    cardHolderName = paymentData.name,
                    expiryDate = paymentData.expiryDate,
                    cvv = paymentData.cvv,
                    isInit = false
                )
            }

            is CardDetailsEvents.OnCardHolderNameChange -> {
                state = state.copy(cardHolderName = event.name)
            }

            is CardDetailsEvents.OnCardNoChange -> {
                state = state.copy(cardNumber = event.number)
            }

            is CardDetailsEvents.OnExpiryDateChange -> {
                state = state.copy(expiryDate = event.date)
            }

            is CardDetailsEvents.OnCvvChange -> {
                state = state.copy(cvv = event.cvv)
            }

            is CardDetailsEvents.Submit -> {
                saveCard()
            }

            is CardDetailsEvents.ShowToast -> {
                state = state.copy(error = "")
            }

            is CardDetailsEvents.OnPaymentResult -> {
                state = state.copy(
                    isLoading = false,
                    error = event.msg,
                    showAlert = true,
                    isApiKeySuccess = false
                )
            }

            is CardDetailsEvents.HideAlert -> {
                state = state.copy(
                    showAlert = false,
                    error = ""
                )
            }

            else -> {}
        }
    }

    private fun saveCard() {
        if (state.categoryItem == null) {
            state = state.copy(error = "No Item in cart to complete payment")
            return
        }

        if (state.cardHolderName.isEmpty()) {
            state = state.copy(error = "Please enter card holder name")
            return
        }

        if (state.cardNumber.isEmpty()) {
            state = state.copy(error = "Please enter card number")
            return
        }

        if (state.expiryDate.isEmpty()) {
            state = state.copy(error = "Please enter expiry date")
            return
        }

        if (state.cvv.isEmpty()) {
            state = state.copy(error = "Please enter cvv")
            return
        }

        state = state.copy(
            isLoading = true,
            error = "",
            isApiKeySuccess = false
        )
        val rPaymentData = RPaymentData().apply {
            cardNumber = state.cardNumber
            name = state.cardHolderName
            expiryDate = state.expiryDate
            cvv = state.cvv
        }
        viewModelScope.launch(Dispatchers.IO) {
            realmHelper.saveCardDetails(rPaymentData)
            val response = apiService.startPayment(
                PaymentBody(
                    amount = "${((state.categoryItem?.pricePerPiece ?: 0.0).toFloat() * 100).toInt()}",
                    currency = "usd",
                )
            )
            state = if (response.success) {
                state.copy(
                    isApiKeySuccess = true,
                    paymentResponse = response.paymentResponse!!,
                    error = ""
                )
            } else {
                state.copy(
                    isApiKeySuccess = false,
                    isLoading = false,
                    error = response.error
                )
            }
        }
    }
}