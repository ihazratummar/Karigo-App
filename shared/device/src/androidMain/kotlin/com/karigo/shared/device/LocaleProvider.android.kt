package com.karigo.shared.device

import android.os.Build
import java.util.Currency
import java.util.Locale

actual class LocaleProvide actual constructor() {
    actual fun getLocationInfo(): LocaleInfo {
        // ✅ Use FORMAT category — respects region setting, not just language
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Locale.getDefault(Locale.Category.FORMAT)
        } else {
            Locale.getDefault()
        }

        val currency = try {
            Currency.getInstance(locale)
        } catch (e: IllegalArgumentException) {
            Currency.getInstance("INR") // fallback
        }

        return LocaleInfo(
            countryCode = locale.country,
            countryName = locale.displayCountry,
            currencyCode = currency.currencyCode,
            currencySymbol = currency.symbol
        )
    }
}