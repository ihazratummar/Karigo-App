package com.karigojob.share.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

object DateUtils {


    fun Long.toReadableDate(
        pattern: DateFormat = DateFormat.DEFAULT
    ): String {
        val timeZone = TimeZone.currentSystemDefault()

        val localDateTime = Instant
            .fromEpochMilliseconds(this)
            .toLocalDateTime(timeZone)

        val currentYear = Clock.System.now()
            .toLocalDateTime(timeZone)
            .year

        val showYear = localDateTime.year != currentYear
        val day = localDateTime.dayOfMonth
        val month = localDateTime.month.name
            .lowercase()
            .replaceFirstChar { it.uppercase() }
            .take(3)

        val minute = localDateTime.minute
            .toString()
            .padStart(2, '0')

        val hour12 = when {
            localDateTime.hour == 0 -> 12
            localDateTime.hour > 12 -> localDateTime.hour - 12
            else -> localDateTime.hour
        }

        val amPm = if (localDateTime.hour >= 12) "pm" else "am"

        val datePart = buildString {
            append("$day $month")

            if (showYear) {
                append(" ${localDateTime.year}")
            }
        }

        val timePart = "$hour12:$minute $amPm"

        return when (pattern) {
            DateFormat.DEFAULT -> "$datePart · $timePart"
            DateFormat.DATE_ONLY -> datePart
            DateFormat.TIME_ONLY -> timePart
        }
    }

}

enum class DateFormat {
    DEFAULT,
    DATE_ONLY,
    TIME_ONLY
}