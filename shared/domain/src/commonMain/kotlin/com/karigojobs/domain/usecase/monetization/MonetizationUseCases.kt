package com.karigojobs.domain.usecase.monetization

import com.karigojobs.domain.repository.MonetizationRepository
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

class ObserveProStatusUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    operator fun invoke(): Flow<ProStatus> = monetizationRepository.observeProStatus()
}

class ObserveMonthlyJobLimitUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    operator fun invoke(): Flow<MonthlyJobLimit> = monetizationRepository.observeMonthlyJobLimit()
}

class GetAvailablePackagesUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    val availablePackages: StateFlow<List<PaywallPackage>>
        get() = monetizationRepository.availablePackages

    val isPriceLoading: StateFlow<Boolean>
        get() = monetizationRepository.isPriceLoading

    /** Triggers a fresh price query — call when connectivity is restored. */
    suspend fun fetchPrices() = monetizationRepository.fetchPrices()
}

class PurchaseProPlanUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    suspend operator fun invoke(tier: PlanTier): Result<Boolean> {
        return monetizationRepository.purchasePlan(tier)
    }
}

class RestorePurchasesUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        return monetizationRepository.restorePurchases()
    }
}

class CheckMonthlyQuotaUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    suspend operator fun invoke(currentYearMonth: String) {
        monetizationRepository.checkAndResetMonthlyQuota(currentYearMonth)
    }
}

class CanCreateJobUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    suspend operator fun invoke(): Boolean {
        return monetizationRepository.canCreateJob()
    }
}

class IncrementJobCountUseCase(
    private val monetizationRepository: MonetizationRepository
) {
    suspend operator fun invoke() {
        monetizationRepository.incrementJobCount()
    }
}


class SetProStatusUseCase(
    private val monetizationRepository: MonetizationRepository
){
    suspend operator fun invoke(isPro: Boolean, tier: PlanTier){
        monetizationRepository.setProStatus(isPro = isPro, tier = tier )
    }
}