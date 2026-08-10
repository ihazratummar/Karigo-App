package com.karigojobs.data.billing

import com.karigojobs.datastore.store.MonetizationStore
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

actual class PlatformBillingProviderImpl(
    private val monetizationStore: MonetizationStore
) : PlatformBillingProvider {

    private val _availablePackages = MutableStateFlow<List<PaywallPackage>>(emptyList())
    actual override val availablePackages: StateFlow<List<PaywallPackage>> = _availablePackages.asStateFlow()

    // iOS StoreKit not integrated yet — stays loading = false (empty list, no shimmer)
    private val _isPriceLoading = MutableStateFlow(false)
    actual override val isPriceLoading: StateFlow<Boolean> = _isPriceLoading.asStateFlow()

    actual override suspend fun queryProductPackages(): List<PaywallPackage> {
        // TODO: Integrate iOS StoreKit 2 when iOS UI is built.
        // Returns empty until real StoreKit products are fetched.
        return emptyList()
    }

    actual override suspend fun launchPurchase(planTier: PlanTier): Result<Boolean> {
        // TODO: Launch StoreKit purchase sheet on iOS
        monetizationStore.setProStatus(isPro = true, tier = planTier)
        return Result.success(true)
    }

    actual override suspend fun restorePurchases(): Result<Boolean> {
        val isPro = monetizationStore.proStatus.value.isProActive
        return Result.success(isPro)
    }
}
