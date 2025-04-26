package com.sanjay.grocery.helper

import android.util.Log
import com.sanjay.grocery.core.Constants.RealmCons.CARD_NUMBER
import com.sanjay.grocery.core.Constants.RealmCons.CATEGORY_ITEM_TYPE_ID
import com.sanjay.grocery.core.Constants.RealmCons.CATEGORY_ITEM_TYPE_NAME
import com.sanjay.grocery.core.Constants.RealmCons.CATEGORY_LIST_ITEM_ID
import com.sanjay.grocery.models.CategoryItems
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.models.PaymentData
import com.sanjay.grocery.models.RCategoryItems
import com.sanjay.grocery.models.RCategoryList
import com.sanjay.grocery.models.RPaymentData
import com.sanjay.grocery.util.ModelUtil.toCategoryItem
import com.sanjay.grocery.util.ModelUtil.toCategoryList
import com.sanjay.grocery.util.ModelUtil.toCategoryThumbnail
import com.sanjay.grocery.util.ModelUtil.toPaymentData
import com.sanjay.grocery.util.ModelUtil.toRCategoryItems
import com.sanjay.grocery.util.ModelUtil.toRCategoryList
import io.realm.Realm
import io.realm.kotlin.executeTransactionAwait
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.Closeable
import java.util.concurrent.Executors
import javax.inject.Inject

class RealmHelper @Inject constructor(
    realmDatabase: Realm,
) : Closeable {
    private val tag = "RealmHelper"

    private val realm = Realm.getInstance(realmDatabase.configuration)
    private val realmConfiguration = realmDatabase.configuration

    private suspend fun runCloseableTransaction(transaction: (realm: Realm) -> Unit) {
        Realm.getInstance(realmConfiguration).use { realmInstance ->
            realmInstance.executeTransactionAwait(transaction = transaction)
        }
    }

    private val singleThreadDispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()

    suspend fun saveCategoryList(categoryList: List<CategoryListItem>) {
        try {
            withContext(singleThreadDispatcher) {
                runCloseableTransaction { transactionRealm ->
                    val ids = categoryList.map { it.categoryID }
                    val results = transactionRealm.where(RCategoryList::class.java)
                        .not()
                        .`in`(CATEGORY_LIST_ITEM_ID, ids.toTypedArray())
                        .findAll()
                    results.deleteAllFromRealm()
                }
            }
            withContext(singleThreadDispatcher) {
                runCloseableTransaction { transactionRealm ->
                    transactionRealm.insertOrUpdate(categoryList.toRCategoryList())
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
    }

    suspend fun saveCategoryItems(categoryItems: List<CategoryItems>) {
        try {
            withContext(singleThreadDispatcher) {
                runCloseableTransaction { transactionRealm ->
                    transactionRealm.where(RCategoryItems::class.java)
                        .findAll()
                        .deleteAllFromRealm()
                }
            }
            withContext(singleThreadDispatcher) {
                runCloseableTransaction { transactionRealm ->
                    transactionRealm.insertOrUpdate(categoryItems.toRCategoryItems())
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
    }

    fun getCategoryList(): List<CategoryListItem> {
        try {
            Realm.getDefaultInstance().use { realm ->
                return realm.where(RCategoryList::class.java).findAll().toCategoryList()
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return emptyList()
    }

    fun getCategoryThumbnailItems(typeId: Int?): List<CategoryItems> {
        try {
            Realm.getDefaultInstance().use { realm ->
                val temp = realm.where(RCategoryItems::class.java)
                    .equalTo(CATEGORY_ITEM_TYPE_ID, typeId)
                    .findAll()
                    .toCategoryThumbnail()
                return temp
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return emptyList()
    }

    override fun close() {
        Log.e(tag, "RealmClose")
        realm.close()
    }

    fun getCategoryItem(typeId: Int, typeName: String): CategoryItems? {
        try {
            val item = realm.where(RCategoryItems::class.java)
                .equalTo(CATEGORY_ITEM_TYPE_ID, typeId)
                .and()
                .equalTo(CATEGORY_ITEM_TYPE_NAME, typeName)
                .findFirst()
            return item?.toCategoryItem()
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return null
    }

    suspend fun updateCategoryItem(typeId: Int?, typeName: String?) {
        try {
            withContext(singleThreadDispatcher) {
                runCloseableTransaction { transactionRealm ->
                    val item = transactionRealm.where(RCategoryItems::class.java)
                        .equalTo(CATEGORY_ITEM_TYPE_ID, typeId)
                        .and()
                        .equalTo(CATEGORY_ITEM_TYPE_NAME, typeName)
                        .findFirst()
                    item?.isFav = !item.isFav
                }
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
    }

    fun getPaymentData(): PaymentData {
        try {
            val item = realm.where(RPaymentData::class.java)
                .findFirst()
            return item.toPaymentData()
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return PaymentData()
    }

    fun getPaymentDataByCardNo(cardNo: String?): PaymentData {
        try {
            val item = realm.where(RPaymentData::class.java)
                .equalTo(CARD_NUMBER, cardNo)
                .findFirst()
            return item.toPaymentData()
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return PaymentData()
    }
}