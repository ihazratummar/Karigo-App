package com.karigojob.share.utils

import com.karigojobs.shared.device.LocaleManager
import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.dateWithTimeIntervalSince1970
import platform.Foundation.localeWithLocaleIdentifier

actual fun Long.formatToLocalizeDate(): String {
    val date = NSDate.dateWithTimeIntervalSince1970(this / 1000.0)
    var appLanguageCode = LocaleManager.getAppLocale()

    // CLDR (the industry standard for locales) defaults to Western digits (0-9) 
    // for many languages like Bengali in technical contexts. 
    // To cleanly override this and force native digits, we append the BCP-47
    // numbering system extension to the locale identifier.
    val baseLanguage = appLanguageCode.split("-").first()
    appLanguageCode = when (baseLanguage) {
        "bn" -> "$appLanguageCode-u-nu-beng"
        "ar" -> "$appLanguageCode-u-nu-arab"
        "hi" -> "$appLanguageCode-u-nu-deva"
        // Add more forced overrides here if needed in the future
        else -> appLanguageCode
    }

    val formatter = NSDateFormatter().apply {
        locale = NSLocale.localeWithLocaleIdentifier(appLanguageCode)
        dateFormat = "d MMM · h:mm a"
    }
    
    return formatter.stringFromDate(date)
}