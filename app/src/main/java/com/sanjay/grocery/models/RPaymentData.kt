package com.sanjay.grocery.models

import io.realm.RealmObject

open class RPaymentData : RealmObject() {
    var name: String? = null
    var cardNumber: String? = null
    var expiryDate: String? = null
    var cvv: String? = null
    var address: String? = null
    var deliveryMethod: String? = null
}