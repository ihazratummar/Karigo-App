package com.karigojobs.data.repository

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToOneOrNull
import com.karigojobs.domain.repository.QuotaRepository
import com.karigojobs.share.model.MonthlyQuotaLimit
import com.karigojobs.shared.database.EpochUtils
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.UuidGenerator
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class QuotaRepositoryImpl(
    private val database: KarigojobsDatabase,
    private val ioDispatcher: CoroutineDispatcher
) : QuotaRepository {

    private fun getCurrentYearMonth(): String {
        return EpochUtils.formatYearMonth(EpochUtils.now())
    }

    private fun ensureCurrentMonthRecord(yearMonth: String) {
        val now = EpochUtils.now()
        database.monthly_quotaQueries.insertOrIgnoreQuota(
            id = yearMonth,
            yearMonth = yearMonth,
            createdAt = now,
            updatedAt = now
        )
    }

    override fun observeCurrentMonthQuota(): Flow<MonthlyQuotaLimit> {
        val yearMonth = getCurrentYearMonth()
        ensureCurrentMonthRecord(yearMonth)

        return database.monthly_quotaQueries
            .getQuotaByMonth(yearMonth)
            .asFlow()
            .mapToOneOrNull(ioDispatcher)
            .map { entity ->
                if (entity != null) {
                    MonthlyQuotaLimit(
                        yearMonth = entity.year_month,
                        usedJobsCount = entity.used_jobs_count.toInt(),
                        usedEstimatesCount = entity.used_estimates_count.toInt(),
                        usedPdfCount = entity.used_pdf_count.toInt()
                    )
                } else {
                    MonthlyQuotaLimit(yearMonth = yearMonth)
                }
            }
    }

    override suspend fun getCurrentMonthQuota(): MonthlyQuotaLimit = withContext(ioDispatcher) {
        val yearMonth = getCurrentYearMonth()
        ensureCurrentMonthRecord(yearMonth)

        val entity = database.monthly_quotaQueries.getQuotaByMonth(yearMonth).executeAsOneOrNull()
        if (entity != null) {
            MonthlyQuotaLimit(
                yearMonth = entity.year_month,
                usedJobsCount = entity.used_jobs_count.toInt(),
                usedEstimatesCount = entity.used_estimates_count.toInt(),
                usedPdfCount = entity.used_pdf_count.toInt()
            )
        } else {
            MonthlyQuotaLimit(yearMonth = yearMonth)
        }
    }

    override suspend fun incrementJobUsage(): Unit = withContext(ioDispatcher) {
        val yearMonth = getCurrentYearMonth()
        ensureCurrentMonthRecord(yearMonth)
        database.monthly_quotaQueries.incrementJobsUsed(
            updatedAt = EpochUtils.now(),
            yearMonth = yearMonth
        )
    }

    override suspend fun incrementEstimateUsage(): Unit = withContext(ioDispatcher) {
        val yearMonth = getCurrentYearMonth()
        ensureCurrentMonthRecord(yearMonth)
        database.monthly_quotaQueries.incrementEstimatesUsed(
            updatedAt = EpochUtils.now(),
            yearMonth = yearMonth
        )
    }

    override suspend fun incrementPdfUsage(): Unit = withContext(ioDispatcher) {
        val yearMonth = getCurrentYearMonth()
        ensureCurrentMonthRecord(yearMonth)
        database.monthly_quotaQueries.incrementPdfUsed(
            updatedAt = EpochUtils.now(),
            yearMonth = yearMonth
        )
    }
}
