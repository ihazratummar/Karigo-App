package com.karigojobs.presentation.monetization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.analytics.AnalyticsEvent
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.usecase.monetization.CheckMonthlyQuotaUseCase
import com.karigojobs.domain.usecase.monetization.GetAvailablePackagesUseCase
import com.karigojobs.domain.usecase.monetization.ObserveMonthlyJobLimitUseCase
import com.karigojobs.domain.usecase.monetization.ObserveProStatusUseCase
import com.karigojobs.domain.usecase.monetization.PurchaseProPlanUseCase
import com.karigojobs.domain.usecase.monetization.RestorePurchasesUseCase
import com.karigojobs.share.model.PlanTier
import com.karigojobs.shared.device.NetworkMonitor
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MonetizationViewModel(
    private val observeProStatusUseCase: ObserveProStatusUseCase,
    private val observeMonthlyJobLimitUseCase: ObserveMonthlyJobLimitUseCase,
    private val getAvailablePackagesUseCase: GetAvailablePackagesUseCase,
    private val purchaseProPlanUseCase: PurchaseProPlanUseCase,
    private val restorePurchasesUseCase: RestorePurchasesUseCase,
    private val checkMonthlyQuotaUseCase: CheckMonthlyQuotaUseCase,
    private val networkMonitor: NetworkMonitor,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    private val _state = MutableStateFlow(MonetizationState())
    val state: StateFlow<MonetizationState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MonetizationEffect>()
    val effect: SharedFlow<MonetizationEffect> = _effect.asSharedFlow()

    init {
        analytics.logScreenView(AnalyticsEvent.Screen.SETTINGS)
        observeProStatus()
        observeMonthlyJobLimit()
        observePriceLoadingState()
        observeAvailablePackages()
        observeNetworkAndFetchPrices()
    }

    fun onEvent(event: MonetizationEvent) {
        when (event) {
            is MonetizationEvent.SelectTab -> {
                _state.update { it.copy(selectedTab = event.planTier) }
            }
            MonetizationEvent.PurchaseSelectedPlan -> handlePurchase()
            MonetizationEvent.RestorePurchases -> handleRestore()
            is MonetizationEvent.CheckMonthlyQuota -> {
                viewModelScope.launch {
                    checkMonthlyQuotaUseCase(event.currentYearMonth)
                }
            }
            MonetizationEvent.ClearError -> {
                _state.update { it.copy(purchaseErrorMessage = null) }
            }
            MonetizationEvent.ClearSuccess -> {
                _state.update { it.copy(successMessage = null) }
            }
            is MonetizationEvent.ToggleCancelDialog -> {
                _state.update { it.copy(isCancelDialogOpen = event.isOpen) }
            }
            MonetizationEvent.OpenGooglePlaySubscriptions -> {
                viewModelScope.launch {
                    val url = "https://play.google.com/store/account/subscriptions?package=com.karigojobs.app"
                    _effect.emit(MonetizationEffect.LaunchGooglePlaySubscriptions(url))
                }
            }
        }
    }

    private fun observeProStatus() {
        viewModelScope.launch {
            observeProStatusUseCase().collectLatest { proStatus ->
                _state.update { it.copy(proStatus = proStatus) }
            }
        }
    }

    private fun observeMonthlyJobLimit() {
        viewModelScope.launch {
            observeMonthlyJobLimitUseCase().collectLatest { quota ->
                _state.update { it.copy(monthlyJobLimit = quota) }
            }
        }
    }

    private fun observePriceLoadingState() {
        viewModelScope.launch {
            getAvailablePackagesUseCase.isPriceLoading.collectLatest { loading ->
                _state.update { it.copy(isLoadingPrices = loading) }
            }
        }
    }

    private fun observeAvailablePackages() {
        viewModelScope.launch {
            getAvailablePackagesUseCase.availablePackages.collectLatest { packages ->
                if (packages.isNotEmpty()) {
                    val monthly = packages.firstOrNull { it.planTier == PlanTier.PRO_MONTHLY }
                    val yearly = packages.firstOrNull { it.planTier == PlanTier.PRO_YEARLY }
                    val lifetime = packages.firstOrNull { it.planTier == PlanTier.PRO_LIFETIME }
                    _state.update { current ->
                        current.copy(
                            monthlyPackage = monthly ?: current.monthlyPackage,
                            yearlyPackage = yearly ?: current.yearlyPackage,
                            lifetimePackage = lifetime ?: current.lifetimePackage
                        )
                    }
                }
            }
        }
    }

    /**
     * Observes connectivity changes.
     * - Reflects offline/online state in UI.
     * - When connectivity is restored and prices haven't been loaded yet,
     *   immediately triggers a fresh price query from Google Play.
     */
    private fun observeNetworkAndFetchPrices() {
        viewModelScope.launch {
            networkMonitor.isOnline
                .collectLatest { online ->
                    _state.update { it.copy(isOffline = !online) }

                    if (online) {
                        val pricesNotYetLoaded = _state.value.monthlyPackage == null ||
                                _state.value.yearlyPackage == null ||
                                _state.value.lifetimePackage == null
                        if (pricesNotYetLoaded) {
                            // Re-trigger billing client query now that we have connectivity
                            getAvailablePackagesUseCase.fetchPrices()
                        }
                    }
                }
        }
    }

    private fun handlePurchase() {
        val selectedTier = _state.value.selectedTab
        viewModelScope.launch {
            _state.update { it.copy(isPurchaseLoading = true) }
            val result = purchaseProPlanUseCase(selectedTier)
            _state.update { it.copy(isPurchaseLoading = false) }
            if (result.isSuccess) {
                _effect.emit(MonetizationEffect.PurchaseSuccess(selectedTier))
                _effect.emit(MonetizationEffect.ShowSuccess("You're now on Pro! Enjoy unlimited jobs."))
            } else {
                val msg = result.exceptionOrNull()?.message
                if (msg != null && !msg.contains("cancelled", ignoreCase = true)) {
                    _effect.emit(MonetizationEffect.ShowError(msg))
                }
            }
        }
    }

    private fun handleRestore() {
        viewModelScope.launch {
            _state.update { it.copy(isRestoreLoading = true) }
            val result = restorePurchasesUseCase()
            _state.update { it.copy(isRestoreLoading = false) }
            if (result.isSuccess) {
                val isRestored = result.getOrDefault(false)
                if (isRestored) {
                    _effect.emit(MonetizationEffect.ShowSuccess("Pro subscription restored successfully!"))
                } else {
                    _effect.emit(MonetizationEffect.ShowError("No active Pro subscription found on this Google Account."))
                }
            } else {
                _effect.emit(MonetizationEffect.ShowError("Unable to restore purchases. Check your network and try again."))
            }
        }
    }
}
