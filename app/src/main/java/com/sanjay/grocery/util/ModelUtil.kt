package com.sanjay.grocery.util

import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.models.RCategoryList
import io.realm.RealmResults

object ModelUtil {

    fun List<CategoryListItem>.toRCategoryList(): List<RCategoryList> {
        return this.map {
            RCategoryList().apply {
                id = it.categoryID
                name = it.categoryName
                type = it.categoryType
                image = it.categoryImage
                total = it.totalItems ?: 0
            }
        }
    }

    fun RealmResults<RCategoryList>.toCategoryList(): List<CategoryListItem> {
        return this.map {
            CategoryListItem(
                categoryID = it.id,
                categoryName = it.name,
                categoryType = it.type,
                categoryImage = it.image,
                totalItems = it.total
            )
        }
    }

}