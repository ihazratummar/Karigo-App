package com.karigojob.share.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

actual fun Long.formatToLocalizeDate(): String {
    val formatter = DateTimeFormatter.ofPattern("d MMM · h:mm a", Locale.getDefault())
    val instant = Instant.ofEpochMilli(this)
    return instant.atZone(ZoneId.systemDefault()).format(formatter)
}