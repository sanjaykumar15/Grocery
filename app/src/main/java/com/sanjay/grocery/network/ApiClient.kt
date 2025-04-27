package com.sanjay.grocery.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.sanjay.grocery.core.Constants
import java.net.HttpURLConnection
import java.net.URL

class ApiClient(
    private val context: Context,
) {

    fun getConnection(
        url: String,
        method: String,
    ): HttpURLConnection {
        val connection =
            URL(Constants.HttpConst.BASE_URL + url).openConnection() as HttpURLConnection
        connection.requestMethod = method
        connection.setRequestProperty("Content-Type", "application/json")
        connection.readTimeout = 600000
        connection.connectTimeout = 600000
        connection.doInput = true

        return connection
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

}