package com.karigojob.share.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

actual fun Long.formatToLocalizeDate(): String {
    val baseLocale = Locale.getDefault()
    
    val formatter = DateTimeFormatter.ofPattern("d MMM · h:mm a", baseLocale)
        
    val instant = Instant.ofEpochMilli(this)
    val formatted = instant.atZone(ZoneId.systemDefault()).format(formatter)
    
    return formatted.map { char ->
        if (char in '0'..'9') {
            when (baseLocale.language) {
                "hi", "mr" -> (char - '0' + 0x0966).toChar()
                "bn" -> (char - '0' + 0x09E6).toChar()
                "ml" -> (char - '0' + 0x0D66).toChar()
                "ta" -> (char - '0' + 0x0BE6).toChar()
                "te" -> (char - '0' + 0x0C66).toChar()
                "ur", "ar" -> (char - '0' + 0x06F0).toChar()
                else -> char
            }
        } else {
            char
        }
    }.joinToString("")
}