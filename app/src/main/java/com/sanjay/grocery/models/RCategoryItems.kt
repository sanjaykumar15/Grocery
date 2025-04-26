package com.sanjay.grocery.models

import io.realm.RealmList
import io.realm.RealmObject

open class RCategoryItems : RealmObject() {
    var typeID: Int? = null
    var typeName: String? = null
    var description: String? = null
    var thumbnailImage: String? = null
    var sliderImages: RealmList<String>? = null
    var pricePerPiece: Float? = null
    var weightPerPiece: Int? = null
    var isFav: Boolean = false
}