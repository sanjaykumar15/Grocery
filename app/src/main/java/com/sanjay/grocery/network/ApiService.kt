package com.sanjay.grocery.network

import android.util.Log
import com.sanjay.grocery.core.ApiResults
import com.sanjay.grocery.core.Constants
import com.sanjay.grocery.core.Constants.HttpConst.GET
import com.sanjay.grocery.core.Constants.HttpConst.POST
import com.sanjay.grocery.core.Constants.HttpConst.START_PAYMENT
import com.sanjay.grocery.helper.RealmHelper
import com.sanjay.grocery.models.CategoryItems
import com.sanjay.grocery.models.CategoryListItem
import com.sanjay.grocery.models.PaymentBody
import com.sanjay.grocery.models.PaymentResponse
import com.sanjay.grocery.util.exceptionUtil
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.OutputStream
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
            Log.e(tag, "Error", e)
            return ApiResults(success = false, error = exceptionUtil(e))
        }
    }

    suspend fun getCategoryItems(type: String, typeId: Int?): ApiResults {
        val con = apiClient.getConnection(
            url = Constants.HttpConst.CATEGORY_ITEMS + type,
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
                Log.d(tag, "Response: $res")
                val responseList = Json.decodeFromString<List<CategoryItems>>(res.toString())
                realmHelper.saveCategoryItems(responseList, typeId)
                return ApiResults(success = true)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
            return ApiResults(success = false, error = exceptionUtil(e))
        }
    }

    fun startPayment(paymentBody: PaymentBody): ApiResults {
        if (!apiClient.isNetworkAvailable()) {
            return ApiResults(success = false, error = "No internet connection")
        }
        val con = apiClient.getConnection(
            url = START_PAYMENT,
            method = POST
        )
        val bodyStr = Json.encodeToJsonElement(paymentBody).toString()
        val os: OutputStream = con.getOutputStream()
        os.write(bodyStr.toByteArray())
        os.close()
        try {
            val reader = InputStreamReader(con.inputStream)
            reader.use { input ->
                val res = StringBuilder()
                val bufferedReader = BufferedReader(input)
                for (line in bufferedReader.readLines()) {
                    res.append(line)
                }
                con.disconnect()
                val response = Json.decodeFromString<PaymentResponse>(res.toString())
                return ApiResults(success = true, paymentResponse = response)
            }
        } catch (e: Exception) {
            Log.e(tag, "Error", e)
            return ApiResults(success = false, error = exceptionUtil(e))
        }
    }

}