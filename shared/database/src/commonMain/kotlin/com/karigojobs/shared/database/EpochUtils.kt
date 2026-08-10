package com.karigojobs.shared.database

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Centralised epoch-millisecond helpers.
 *
 * Every `created_at` / `updated_at` / `job_date` column stores **UTC epoch ms** (Long).
 * Use these helpers so the rest of the code never touches raw time APIs directly.
 */
object EpochUtils {

    /** Current UTC instant as epoch milliseconds. */
    fun now(): Long = Clock.System.now().toEpochMilliseconds()

    /** Convert epoch ms → [Instant]. */
    fun toInstant(epochMs: Long): Instant = Instant.fromEpochMilliseconds(epochMs)

    /**
     * Returns the epoch ms for the **start** of the current calendar month (UTC midnight).
     * Useful for `countJobsThisMonth` query parameter.
     */
    fun startOfCurrentMonthMs(): Long {
        val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val startOfMonth = LocalDate(today.year, today.month.number, 1)
        return startOfMonth.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    }

    /**
     * Returns the epoch ms for the **start** of the next calendar month (UTC midnight).
     * Useful as the upper-bound for `countJobsThisMonth`.
     */
    fun startOfNextMonthMs(): Long {
        val today: LocalDate = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        val nextMonth = if (today.month.number == 12) {
            LocalDate(today.year + 1, 1, 1)
        } else {
            LocalDate(today.year, today.month.number + 1, 1)
        }
        return nextMonth.atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
    }

    fun getMonthIndexFromEpoch(epochMs: Long): Int {
        val dt = Instant.fromEpochMilliseconds(epochMs).toLocalDateTime(TimeZone.UTC)
        return dt.month.number - 1
    }

    fun getCurrentMonthIndex(): Int {
        val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
        return today.month.number - 1
    }

    fun formatYearMonth(epochMs: Long = now()): String {
        val dt = Instant.fromEpochMilliseconds(epochMs).toLocalDateTime(TimeZone.UTC)
        val monthStr = dt.month.number.toString().padStart(2, '0')
        return "${dt.year}-$monthStr"
    }
}
