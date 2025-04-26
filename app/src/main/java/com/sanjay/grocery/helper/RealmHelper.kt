package com.sanjay.grocery.helper

import android.util.Log
import com.sanjay.grocery.core.Constants.RealmCons.CATEGORY_LIST_ITEM_ID
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.models.RCategoryList
import com.sanjay.grocery.util.ModelUtil.toCategoryList
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

    fun getCategoryList(): List<CategoryListItem> {
        try {
            val realm = Realm.getInstance(realmConfiguration)
            return realm.where(RCategoryList::class.java).findAll().toCategoryList()
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
        }
        return emptyList()
    }

    override fun close() {
        Log.e(tag, "RealmClose")
        realm.close()
    }
}