package com.karigojobs.shared.device

import java.util.Currency
import java.util.Locale

actual class LocaleProvide actual constructor() {
    actual fun getLocationInfo(): LocaleInfo {
        val locale = Locale.getDefault(Locale.Category.FORMAT)
        val countryCode = locale.country.uppercase()

        val currencySymbol = try {
            val curr = Currency.getInstance(locale)
            when {
                countryCode == "IN" -> "₹"
                countryCode == "RU" -> "₽"
                countryCode == "AE" -> "AED"
                countryCode == "SA" -> "SAR"
                countryCode == "BD" -> "৳"
                countryCode == "PK" -> "₨"
                countryCode == "NP" -> "Rs"
                countryCode == "LK" -> "Rs"
                countryCode == "QA" -> "QAR"
                countryCode == "OM" -> "OMR"
                countryCode == "KW" -> "KWD"
                countryCode == "BH" -> "BHD"
                countryCode == "EG" -> "E£"
                countryCode == "NG" -> "₦"
                countryCode == "KE" -> "KSh"
                countryCode == "ZA" -> "R"
                curr != null -> curr.symbol
                else -> "₹"
            }
        } catch (e: Exception) {
            "₹"
        }

        return LocaleInfo(
            countryCode = countryCode.ifBlank { "IN" },
            countryName = locale.displayCountry.ifBlank { "India" },
            currencyCode = try { Currency.getInstance(locale).currencyCode } catch (e: Exception) { "INR" },
            currencySymbol = currencySymbol
        )
    }
}