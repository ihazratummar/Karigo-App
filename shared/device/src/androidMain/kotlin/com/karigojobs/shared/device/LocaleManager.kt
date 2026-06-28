package com.karigojobs.shared.device

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat

actual object LocaleManager {
    actual fun setAppLocale(languageCode: String) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageCode)
        )
    }

    actual fun getAppLocale(): String {
        val locales = AppCompatDelegate.getApplicationLocales()
        return if (!locales.isEmpty) {
            locales.get(0)?.language ?: "en"
        } else {
            "en"
        }
    }
}
