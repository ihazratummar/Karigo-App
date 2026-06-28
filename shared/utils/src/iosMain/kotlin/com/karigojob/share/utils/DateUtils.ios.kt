package com.karigojob.share.utils

import platform.Foundation.NSDate
import platform.Foundation.NSDateFormatter
import platform.Foundation.NSLocale
import platform.Foundation.currentLocale
import platform.Foundation.dateWithTimeIntervalSince1970
actual fun Long.formatToLocalizeDate(): String {
    // Use the factory method without the named parameter
    val date = NSDate.dateWithTimeIntervalSince1970(this / 1000.0)

    val formatter = NSDateFormatter().apply {
        locale = NSLocale.currentLocale // Automatically uses system language
        dateFormat = "d MMM · h:mm a"
    }
    return formatter.stringFromDate(date)
}