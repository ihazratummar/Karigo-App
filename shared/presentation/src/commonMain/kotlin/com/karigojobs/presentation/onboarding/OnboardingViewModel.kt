package com.karigojobs.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.usecase.CompleteOnboardingUseCase
import com.karigojobs.domain.usecase.GetOnboardingStatusUseCase
import com.karigojobs.domain.usecase.SeedStarterMaterialsUseCase
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * @author hazratummar
 * Created on 21/05/26
 */

class OnboardingViewModel(
    private val completeOnboardingUseCase: CompleteOnboardingUseCase,
    private val getOnboardingStatusUseCase: GetOnboardingStatusUseCase,
    private val seedStarterMaterialsUseCase: SeedStarterMaterialsUseCase
) : ViewModel() {

    // ── STATE ─────────────────────────────────────────────────────────────────
    private val _state = MutableStateFlow(OnboardingState())
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    // ── EFFECTS ───────────────────────────────────────────────────────────────
    // replay = 0 → never replays on resubscription (correct for navigation)
    private val _effects = MutableSharedFlow<OnboardingEffect>(replay = 0)
    val effect = _effects.asSharedFlow()

    val completedState: StateFlow<OnboardingCompleteState> = getOnboardingStatusUseCase()
        .map { completed ->
            if (completed) OnboardingCompleteState.Completed
            else OnboardingCompleteState.NotCompleted
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = OnboardingCompleteState.Loading
        )

    // ── INTENT HANDLER ────────────────────────────────────────────────────────
    fun onIntent(intent: OnboardingIntent) {
        when (intent) {
            OnboardingIntent.GetStarted -> handleGeStarted()
            is OnboardingIntent.ToggleTrade -> handleToggleTrade(intent.trade)
            OnboardingIntent.ConfirmTrades -> handleConfirmTrade()
            OnboardingIntent.BackToTrades -> handleBackToTrades()
            OnboardingIntent.LetsGo -> handleLetsGo()
            OnboardingIntent.DismissError -> clearError()
        }
    }

    // ── HANDLERS ──────────────────────────────────────────────────────────────

    private fun handleGeStarted() {
        _state.update { it.copy(currentStep = OnboardingStep.TRADE_SELECT) }
    }

    private fun handleToggleTrade(tradeType: TradeType) {
        _state.update { current ->
            val updatedTrades = current.selectedTrades.toMutableSet()
            if (tradeType in updatedTrades) {
                updatedTrades.remove(tradeType)
            } else {
                updatedTrades.add(tradeType)
            }

            current.copy(selectedTrades = updatedTrades)
        }
    }

    private fun handleConfirmTrade() {
        // Guard - should never be called with empty set
        // but defensive check here too
        if (_state.value.selectedTrades.isEmpty()) return
        _state.update { it.copy(currentStep = OnboardingStep.READY) }
    }

    private fun handleBackToTrades() {
        _state.update { it.copy(currentStep = OnboardingStep.TRADE_SELECT) }
    }

    private fun handleLetsGo() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                // 1. seed material into SQLDelight Meterial table
                val count = seedStarterMaterialsUseCase.invoke(trade = _state.value.selectedTrades)
                _state.update { it.copy(totalMaterialCount = count) }

                // 2. Write is_complete = true + selected trades to Datastore
                completeOnboardingUseCase(trades = _state.value.selectedTrades)

                // 3. update state
                _state.update { it.copy(isLoading = false, seededMaterialCount = 1) }

                // 4. fire navigation effect - clear back stack on Android,
                // switches AppFeature.State on IOS

                _effects.emit(OnboardingEffect.NavigationToDashboard)

            }catch (e: Exception){
                _state.update {
                    it.copy(
                        isLoading = false,
                        error = e.message ?: "Something went wrong"
                    )
                }

                _effects.emit(
                    OnboardingEffect.ShowError(
                        message = e.message ?: "Something went wrong"
                    )
                )
            }

        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

}