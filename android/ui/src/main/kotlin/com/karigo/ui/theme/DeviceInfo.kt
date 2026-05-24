package com.karigo.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.karigo.shared.device.LocaleProvide


/**
 * @author hazratummar
 * Created on 24/05/26
 */
 

data class DeviceInfo(
    val currency: String = "",
    val countryName: String = ""
)

fun getDeviceInfo(): DeviceInfo {
    val info = LocaleProvide().getLocationInfo()
    return DeviceInfo(
        currency = info.currencySymbol,
        countryName = info.countryName
    )
}