package com.karigojobs.domain.repository

import com.karigojobs.share.model.MonthlyQuotaLimit
import kotlinx.coroutines.flow.Flow

interface QuotaRepository {
    fun observeCurrentMonthQuota(): Flow<MonthlyQuotaLimit>
    suspend fun getCurrentMonthQuota(): MonthlyQuotaLimit
    suspend fun incrementJobUsage()
    suspend fun incrementEstimateUsage()
    suspend fun incrementPdfUsage()
}
