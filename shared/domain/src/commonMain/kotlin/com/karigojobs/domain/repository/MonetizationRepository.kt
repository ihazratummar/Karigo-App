package com.karigojobs.domain.repository

import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MonetizationRepository {
    val availablePackages: StateFlow<List<PaywallPackage>>
    val isPriceLoading: StateFlow<Boolean>
    fun observeProStatus(): Flow<ProStatus>
    fun observeMonthlyJobLimit(): Flow<MonthlyJobLimit>
    suspend fun getProStatus(): ProStatus
    suspend fun getMonthlyJobLimit(): MonthlyJobLimit
    suspend fun canCreateJob(): Boolean
    suspend fun incrementJobCount()
    suspend fun setProStatus(
        isPro: Boolean,
        tier: PlanTier,
        purchaseDateEpochMs: Long? = null,
        expirationDateEpochMs: Long? = null
    )
    suspend fun purchasePlan(tier: PlanTier): Result<Boolean>
    suspend fun checkAndResetMonthlyQuota(currentYearMonth: String)
    suspend fun restorePurchases(): Result<Boolean>
    suspend fun fetchPrices()
}
