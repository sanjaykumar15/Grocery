package com.sanjay.grocery.core

object Constants {

    object RealmCons {
        const val DB_NAME = "grocery.realm"
        const val CATEGORY_LIST_ITEM_ID = "id"
        const val CATEGORY_ITEM_TYPE_ID = "typeID"
        const val CATEGORY_ITEM_TYPE_NAME = "typeName"
        const val CARD_NUMBER = "cardNumber"
    }

    object HttpConst {
        const val POST = "POST"
        const val GET = "GET"

        const val BASE_URL = "https://assignment.musewearables.com/"

        const val CATEGORY_LIST = "router/get/all/categories"
        const val CATEGORY_ITEMS = "router/get/category/"
    }

    const val CHANGE_FOR_CARD = "ChangeForCard"
    const val CHANGE_FOR_ADDRESS = "ChangeForAddress"
    const val CHANGE_FOR_DELIVERY = "ChangeForDelivery"

    const val BY_MYSELF = "By Address"
    const val BY_COURIER = "By Courier"
    const val BY_DRONE = "By Drone"

}