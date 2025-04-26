package com.sanjay.grocery.util

import android.accounts.NetworkErrorException
import android.util.Log
import kotlinx.coroutines.CancellationException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun exceptionUtil(e: Exception): String {
    when (e) {
        is NetworkErrorException -> {
            Log.e("NetworkErrorException", e.message.toString())
            return "No Internet."
        }

        is SocketTimeoutException -> {
            Log.e("SocketTimeoutException", e.message.toString())
            return "Connection Timeout."
        }

        is UnknownHostException -> {
            Log.e("UnknownHostException", e.message.toString())
            return "Unable to connect."
        }

        is IOException -> {
            Log.e("IOException", e.message.toString())
            return "Unable to Connect."
        }

        is CancellationException -> {
            Log.e("CoroutineExceptionHandler", e.message.toString())
            return "Skip"
        }

        else -> {
            Log.e("exceptionUtil", e.message.toString())
            return "Something went wrong."
        }
    }
}