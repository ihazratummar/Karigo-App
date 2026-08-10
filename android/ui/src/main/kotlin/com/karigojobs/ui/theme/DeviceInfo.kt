package com.karigojobs.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.karigojobs.shared.device.LocaleProvide

val LocalDeviceInfo = compositionLocalOf { getDeviceInfo() }

data class DeviceInfo(
    val currency: String = "",
    val countryName: String = ""
)

fun getDeviceInfo(savedCurrencySymbol: String? = null): DeviceInfo {
    val info = LocaleProvide().getLocationInfo()
    val activeCurrency = if (!savedCurrencySymbol.isNullOrBlank()) {
        savedCurrencySymbol
    } else {
        info.currencySymbol
    }
    return DeviceInfo(
        currency = activeCurrency,
        countryName = info.countryName
    )
}