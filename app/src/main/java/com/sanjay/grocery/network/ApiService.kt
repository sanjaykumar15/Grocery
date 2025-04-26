package com.sanjay.grocery.network

import android.util.Log
import com.sanjay.grocery.core.ApiResults
import com.sanjay.grocery.core.Constants
import com.sanjay.grocery.core.Constants.HttpConst.GET
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.util.exceptionUtil
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class ApiService @Inject constructor(
    private var apiClient: ApiClient,
    private var realmHelper: RealmHelper,
) {

    private val tag = "ApiService"

    suspend fun getCategories(): ApiResults {
        val con = apiClient.getConnection(
            url = Constants.HttpConst.CATEGORY_LIST,
            method = GET
        )
        try {
            val reader = InputStreamReader(con.inputStream)
            reader.use { input ->
                val res = StringBuilder()
                val bufferedReader = BufferedReader(input)
                for (line in bufferedReader.readLines()) {
                    res.append(line)
                }
                con.disconnect()
                val responseList = Json.decodeFromString<List<CategoryListItem>>(res.toString())
                realmHelper.saveCategoryList(responseList)
                return ApiResults(success = true)
            }
        } catch (e: Exception) {
            return ApiResults(success = false, error = exceptionUtil(e))
            Log.e(tag, "Error", e)
        }
    }

}