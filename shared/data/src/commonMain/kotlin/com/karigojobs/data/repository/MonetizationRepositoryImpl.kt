package com.karigojobs.data.repository

import com.karigojobs.data.billing.PlatformBillingProviderImpl
import com.karigojobs.datastore.store.MonetizationStore
import com.karigojobs.domain.repository.MonetizationRepository
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class MonetizationRepositoryImpl(
    private val monetizationStore: MonetizationStore,
    private val billingProvider: PlatformBillingProviderImpl
) : MonetizationRepository {

    override val availablePackages: StateFlow<List<PaywallPackage>>
        get() = billingProvider.availablePackages

    override val isPriceLoading: StateFlow<Boolean>
        get() = billingProvider.isPriceLoading

    override fun observeProStatus(): Flow<ProStatus> {
        return monetizationStore.proStatus
    }

    override fun observeMonthlyJobLimit(): Flow<MonthlyJobLimit> {
        return monetizationStore.monthlyJobLimit
    }

    override suspend fun getProStatus(): ProStatus {
        return monetizationStore.proStatus.value
    }

    override suspend fun getMonthlyJobLimit(): MonthlyJobLimit {
        return monetizationStore.monthlyJobLimit.value
    }

    override suspend fun canCreateJob(): Boolean {
        val pro = getProStatus()
        if (pro.hasProAccess) return true
        val quota = getMonthlyJobLimit()
        return !quota.isQuotaExhausted
    }

    override suspend fun incrementJobCount() {
        val currentQuota = getMonthlyJobLimit()
        val currentMonth = monetizationStore.lastResetYearMonth.value
        monetizationStore.setMonthlyJobCount(
            count = currentQuota.usedJobsCount + 1,
            yearMonth = currentMonth
        )
    }

    override suspend fun setProStatus(
        isPro: Boolean,
        tier: PlanTier,
        purchaseDateEpochMs: Long?,
        expirationDateEpochMs: Long?
    ) {
        monetizationStore.setProStatus(
            isPro = isPro,
            tier = tier,
            purchaseDateEpochMs = purchaseDateEpochMs,
            expirationDateEpochMs = expirationDateEpochMs
        )
    }

    override suspend fun purchasePlan(tier: PlanTier): Result<Boolean> {
        return billingProvider.launchPurchase(tier)
    }

    override suspend fun checkAndResetMonthlyQuota(currentYearMonth: String) {
        val storedMonth = monetizationStore.lastResetYearMonth.value
        if (storedMonth != currentYearMonth) {
            monetizationStore.setMonthlyJobCount(count = 0, yearMonth = currentYearMonth)
        }
    }

    override suspend fun restorePurchases(): Result<Boolean> {
        return billingProvider.restorePurchases()
    }

    override suspend fun fetchPrices() {
        billingProvider.queryProductPackages()
    }
}
