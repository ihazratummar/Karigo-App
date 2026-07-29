package com.karigojob.share.utils

/**
 * Reusable Logger for Kotlin Multiplatform.
 * Can be used anywhere across presentation, domain, data, database, and UI modules.
 *
 * Usage:
 *   AppLogger.d("Debugging info...")
 *   AppLogger.i("User logged in")
 *   AppLogger.w("Low memory warning")
 *   AppLogger.e("Database operation failed", exception)
 *   AppLogger.d("Custom Tag", "My message")
 */
object AppLogger {

    var isEnabled: Boolean = true
    private const val DEFAULT_TAG = "KarigoApp"

    fun d(message: Any?, tag: String = DEFAULT_TAG) {
        if (isEnabled) log("DEBUG", tag, message.toString())
    }

    fun i(message: Any?, tag: String = DEFAULT_TAG) {
        if (isEnabled) log("INFO", tag, message.toString())
    }

    fun w(message: Any?, tag: String = DEFAULT_TAG) {
        if (isEnabled) log("WARN", tag, message.toString())
    }

    fun e(message: Any?, throwable: Throwable? = null, tag: String = DEFAULT_TAG) {
        if (isEnabled) {
            val errDetails = throwable?.let { "\n${it.stackTraceToString()}" } ?: ""
            log("ERROR", tag, "${message.toString()}$errDetails")
        }
    }

    private fun log(level: String, tag: String, message: String) {
        println("[$level][$tag] $message")
    }
}
