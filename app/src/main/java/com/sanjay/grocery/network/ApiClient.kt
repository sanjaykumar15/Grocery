package com.sanjay.grocery.network

import com.sanjay.grocery.core.Constants
import java.net.HttpURLConnection
import java.net.URL

class ApiClient {

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

}