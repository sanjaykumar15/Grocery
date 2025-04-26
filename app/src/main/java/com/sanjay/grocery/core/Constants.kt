package com.sanjay.grocery.core

object Constants {

    object RealmCons {
        const val DB_NAME = "grocery.realm"
        const val CATEGORY_LIST_ITEM_ID = "id"
        const val CATEGORY_ITEM_TYPE_ID = "typeID"
        const val CATEGORY_ITEM_TYPE_NAME = "typeName"
    }

    object HttpConst {
        const val POST = "POST"
        const val GET = "GET"

        const val BASE_URL = "https://assignment.musewearables.com/"

        const val CATEGORY_LIST = "router/get/all/categories"
        const val CATEGORY_ITEMS = "router/get/category/"
    }
}