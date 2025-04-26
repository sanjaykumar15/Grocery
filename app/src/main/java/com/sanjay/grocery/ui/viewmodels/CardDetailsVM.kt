package com.sanjay.grocery.ui.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.models.RPaymentData
import com.sanjay.grocery.ui.events.CardDetailsEvents
import com.sanjay.grocery.ui.states.CardDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardDetailsVM @Inject constructor(
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

            else -> {}
        }
    }

    private fun saveCard() {
        val rPaymentData = RPaymentData().apply {
            cardNumber = state.cardNumber
            name = state.cardHolderName
            expiryDate = state.expiryDate
            cvv = state.cvv
        }
        viewModelScope.launch(Dispatchers.IO) {
            realmHelper.saveCardDetails(rPaymentData)
        }
    }
}