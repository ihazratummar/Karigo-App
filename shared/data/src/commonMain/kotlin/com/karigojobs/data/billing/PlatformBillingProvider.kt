package com.karigojobs.data.billing

import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import kotlinx.coroutines.flow.StateFlow

interface PlatformBillingProvider {
    val availablePackages: StateFlow<List<PaywallPackage>>
    val isPriceLoading: StateFlow<Boolean>
    suspend fun queryProductPackages(): List<PaywallPackage>
    suspend fun launchPurchase(planTier: PlanTier): Result<Boolean>
    suspend fun restorePurchases(): Result<Boolean>
}

expect class PlatformBillingProviderImpl : PlatformBillingProvider {
    override val availablePackages: StateFlow<List<PaywallPackage>>
    override val isPriceLoading: StateFlow<Boolean>
    override suspend fun queryProductPackages(): List<PaywallPackage>
    override suspend fun launchPurchase(planTier: PlanTier): Result<Boolean>
    override suspend fun restorePurchases(): Result<Boolean>
}
