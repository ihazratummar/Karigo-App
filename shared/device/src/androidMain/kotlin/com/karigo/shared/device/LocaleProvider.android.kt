package com.karigo.shared.device

import java.util.Currency
import java.util.Locale

actual class LocaleProvide actual constructor() {
    actual fun getLocationInfo(): LocaleInto {
        val locale = Locale.getDefault()

        val currency = Currency.getInstance(locale)

        return LocaleInto(
            countryCode = locale.country,
            countryName = locale.displayCountry,
            currencyCode = currency.currencyCode,
            currencySymbol = currency.symbol
        )
    }
}