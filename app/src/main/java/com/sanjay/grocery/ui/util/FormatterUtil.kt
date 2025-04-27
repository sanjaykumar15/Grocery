package com.sanjay.grocery.ui.util

object FormatterUtil {

    fun cardNumFormat(value: String): String {
        val trimmer = if (value.length >= 16) value.substring(0..15) else value
        var out = ""

        for (i in trimmer.indices) {
            out += trimmer[i]
            if (i == 3) out += " "
            if (i == 7) out += " "
            if (i == 11) out += " "
        }

        return out
    }

    fun expireDateFormat(value: String): String {
        val trimmed = if (value.length >= 4) value.substring(0..3) else value
        var out = ""

        for (i in trimmed.indices) {
            out += trimmed[i]
            if (i == 1) out += "/"
        }

        return out
    }

    fun cardHolderFormat(value: String): String {
        return value.uppercase()
    }

}