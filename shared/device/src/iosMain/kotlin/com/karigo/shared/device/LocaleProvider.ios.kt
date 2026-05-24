package com.karigo.shared.device

import platform.Foundation.NSLocale
import platform.Foundation.NSLocaleCountryCode
import platform.Foundation.currencyCode
import platform.Foundation.currencySymbol
import platform.Foundation.currentLocale

actual class LocaleProvide actual constructor() {
    actual fun getLocationInfo(): LocaleInfo {
        val locale = NSLocale.currentLocale
        val countryCode = locale.objectForKey(NSLocaleCountryCode) as? String ?: ""
        val countryName = locale.displayNameForKey(NSLocaleCountryCode, countryCode) ?: ""

        return LocaleInfo(
            countryCode = countryCode,
            countryName = countryName,
            currencyCode = locale.currencyCode ?: "",
            currencySymbol = locale.currencySymbol ?: ""
        )
    }
}