package com.karigojob.share.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

actual fun Long.formatToLocalizeDate(): String {
    val baseLocale = Locale.getDefault()
    
    // CLDR (the industry standard for locales) defaults to Western digits (0-9) 
    // for many languages like Bengali in technical contexts. 
    // To cleanly override this and force native digits, we use the official 
    // Unicode Extension "nu" (Numbering System).
    val numberingSystem = when (baseLocale.language) {
        "bn" -> "beng" // Force Bengali digits
        "ar" -> "arab" // Force Arabic digits
        "hi" -> "deva" // Force Devanagari digits
        // Add more forced overrides here if needed in the future
        else -> null
    }

    val locale = if (numberingSystem != null) {
        Locale.Builder()
            .setLocale(baseLocale)
            .setExtension(Locale.UNICODE_LOCALE_EXTENSION, "nu-$numberingSystem")
            .build()
    } else {
        baseLocale
    }

    val formatter = DateTimeFormatter.ofPattern("d MMM · h:mm a", locale)
        .withDecimalStyle(java.time.format.DecimalStyle.of(locale))
        
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}