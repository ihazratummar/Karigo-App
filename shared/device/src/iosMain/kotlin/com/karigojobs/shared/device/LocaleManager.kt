package com.karigojobs.shared.device

import platform.Foundation.NSUserDefaults

actual object LocaleManager {
    actual fun setAppLocale(languageCode: String) {
        NSUserDefaults.standardUserDefaults.setObject(arrayListOf(languageCode), "AppleLanguages")
        NSUserDefaults.standardUserDefaults.synchronize()
    }

    actual fun getAppLocale(): String {
        val languages = NSUserDefaults.standardUserDefaults.stringArrayForKey("AppleLanguages")
        return languages?.firstOrNull() as? String ?: "en"
    }
}
