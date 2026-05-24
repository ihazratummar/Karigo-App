package com.karigo.shared.device


/**
 * @author hazratummar
 * Created on 23/05/26
 */


data class LocaleInfo(
    val countryCode: String,
    val countryName: String,
    val currencyCode: String,
    val currencySymbol: String
)


expect class LocaleProvide() {
    fun getLocationInfo() : LocaleInfo
}