package com.karigojobs.ui.theme

import androidx.compose.runtime.compositionLocalOf
import com.karigojobs.shared.device.LocaleProvide


/**
 * @author hazratummar
 * Created on 24/05/26
 */
 

val LocalDeviceInfo = compositionLocalOf { getDeviceInfo() }

data class DeviceInfo(
    val currency: String = "",
    val countryName: String = ""
)

fun getDeviceInfo(): DeviceInfo {
    val info = com.karigojobs.shared.device.LocaleProvide().getLocationInfo()
    return DeviceInfo(
        currency = info.currencySymbol,
        countryName = info.countryName
    )
}