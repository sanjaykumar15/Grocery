package com.sanjay.grocery.util

import com.sanjay.grocery.models.CategoryItems
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.models.PaymentData
import com.sanjay.grocery.models.RCategoryItems
import com.sanjay.grocery.models.RCategoryList
import com.sanjay.grocery.models.RPaymentData
import io.realm.RealmList
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

    fun List<CategoryItems>.toRCategoryItems(): List<RCategoryItems> {
        return this.map { item ->
            RCategoryItems().apply {
                this.typeID = item.typeID
                this.typeName = item.typeName
                this.description = item.description
                this.pricePerPiece = item.pricePerPiece
                this.thumbnailImage = item.thumbnailImage
                this.weightPerPiece = item.weightPerPiece
                this.sliderImages = RealmList<String>().apply {
                    addAll(item.sliderImages)
                }
            }
        }
    }

    fun RealmResults<RCategoryItems>.toCategoryThumbnail(): List<CategoryItems> {
        return this.map {
            CategoryItems(
                typeID = it.typeID,
                typeName = it.typeName,
                pricePerPiece = it.pricePerPiece,
                thumbnailImage = it.thumbnailImage
            )
        }
    }

    fun RCategoryItems.toCategoryItem(): CategoryItems {
        return CategoryItems(
            typeID = this.typeID,
            typeName = this.typeName,
            description = this.description,
            pricePerPiece = this.pricePerPiece,
            thumbnailImage = this.thumbnailImage,
            weightPerPiece = this.weightPerPiece,
            sliderImages = this.sliderImages?.toMutableList() ?: emptyList(),
            isFav = this.isFav
        )
    }

    fun RPaymentData?.toPaymentData(): PaymentData {
        if (this == null)
            return PaymentData()
        return PaymentData(
            name = this.name ?: "",
            cardNumber = this.cardNumber ?: "",
            address = this.address ?: "",
            expiryDate = this.expiryDate ?: "",
            cvv = this.cvv ?: "",
            deliveryMethod = this.deliveryMethod ?: ""
        )
    }

}