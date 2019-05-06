package com.sqli.guildes.utils

import log
import java.io.IOException
import java.util.concurrent.TimeoutException

object ErrorUtil {
    fun handleError(error: Throwable, caller: String) : String {
        error.localizedMessage?.let {
            log("ERROR $caller -> $it")
        } ?: log("ERROR $caller ->")
                .also {
                    error.printStackTrace()
                }
        return when (error) {
            is IOException -> "Please check your internet connection"
            is TimeoutException -> "Request timed out"
            else -> "An error occurred"
        }
    }
}