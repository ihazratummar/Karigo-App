package com.karigojobs.presentation.monetization

import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PaywallPackage
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus

data class MonetizationState(
    val isLoading: Boolean = false,
    val isLoadingPrices: Boolean = true,   // true until Google Play responds
    val isOffline: Boolean = false,         // true when no internet connection
    val isPurchaseLoading: Boolean = false,
    val isRestoreLoading: Boolean = false,
    val proStatus: ProStatus = ProStatus(),
    val monthlyJobLimit: MonthlyJobLimit = MonthlyJobLimit(),
    val selectedTab: PlanTier = PlanTier.PRO_MONTHLY,
    val monthlyPackage: PaywallPackage? = null,
    val yearlyPackage: PaywallPackage? = null,
    val lifetimePackage: PaywallPackage? = null,
    val purchaseErrorMessage: String? = null,
    val successMessage: String? = null,
    val isCancelDialogOpen: Boolean = false
)

sealed interface MonetizationEvent {
    data class SelectTab(val planTier: PlanTier) : MonetizationEvent
    data object PurchaseSelectedPlan : MonetizationEvent
    data object RestorePurchases : MonetizationEvent
    data class CheckMonthlyQuota(val currentYearMonth: String) : MonetizationEvent
    data object ClearError : MonetizationEvent
    data object ClearSuccess : MonetizationEvent
    data class ToggleCancelDialog(val isOpen: Boolean) : MonetizationEvent
    data object OpenGooglePlaySubscriptions : MonetizationEvent
}

sealed interface MonetizationEffect {
    data class PurchaseSuccess(val planTier: PlanTier) : MonetizationEffect
    data class ShowError(val message: String) : MonetizationEffect
    data class ShowSuccess(val message: String) : MonetizationEffect
    data class LaunchGooglePlaySubscriptions(val url: String) : MonetizationEffect
}
