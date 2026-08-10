package com.karigojobs.data.repository

import com.karigojobs.data.billing.PlatformBillingProvider
import com.karigojobs.datastore.store.MonetizationStore
import com.karigojobs.domain.repository.MonetizationRepository
import com.karigojobs.domain.repository.QuotaRepository
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class MonetizationRepositoryImpl(
    private val monetizationStore: MonetizationStore,
    private val billingProvider: PlatformBillingProvider,
    private val quotaRepository: QuotaRepository
) : MonetizationRepository {

    override val availablePackages: StateFlow<List<PaywallPackage>>
        get() = billingProvider.availablePackages

    override val isPriceLoading: StateFlow<Boolean>
        get() = billingProvider.isPriceLoading

    override fun observeProStatus(): Flow<ProStatus> {
        return monetizationStore.proStatus
    }

    override fun observeMonthlyJobLimit(): Flow<MonthlyJobLimit> {
        return quotaRepository.observeCurrentMonthQuota()
    }

    override suspend fun getProStatus(): ProStatus {
        return monetizationStore.proStatus.value
    }

    override suspend fun getMonthlyJobLimit(): MonthlyJobLimit {
        return quotaRepository.getCurrentMonthQuota()
    }

    override suspend fun canCreateJob(): Boolean {
        val pro = getProStatus()
        if (pro.hasProAccess) return true
        val quota = getMonthlyJobLimit()
        return !quota.isJobsQuotaExhausted
    }

    override suspend fun incrementJobCount() {
        quotaRepository.incrementJobUsage()
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
        quotaRepository.getCurrentMonthQuota()
    }

    override suspend fun restorePurchases(): Result<Boolean> {
        return billingProvider.restorePurchases()
    }

    override suspend fun fetchPrices() {
        billingProvider.queryProductPackages()
    }
}
