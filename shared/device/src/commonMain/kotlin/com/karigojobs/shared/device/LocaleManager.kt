package com.karigojobs.shared.device


/**
 * @author hazratummar
 * Created on 28/06/26
 */

expect object LocaleManager {
    fun setAppLocale(languageCode: String)
    fun getAppLocale(): String
}