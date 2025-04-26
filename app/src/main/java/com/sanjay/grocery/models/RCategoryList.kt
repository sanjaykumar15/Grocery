package com.sanjay.grocery.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RCategoryList : RealmObject() {
    @PrimaryKey
    var id: Int? = null
    var type: String? = null
    var name: String? = null
    var image: String? = null
    var total: Int = 0
}