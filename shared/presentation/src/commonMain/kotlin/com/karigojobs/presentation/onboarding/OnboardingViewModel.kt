package com.karigojobs.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.onboarding.CompleteOnboardingUseCase
import com.karigojobs.domain.usecase.onboarding.GetOnboardingStatusUseCase
import com.karigojobs.domain.usecase.material.SeedStarterMaterialsUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.share.model.TradeType
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.domain.usecase.settings.UpdateAppLanguageUseCase
import com.karigojobs.share.model.AppLanguage
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
    private val seedStarterMaterialsUseCase: SeedStarterMaterialsUseCase,
    private val analytics: AnalyticsLogger,
    private val updateAppLanguageUseCase: UpdateAppLanguageUseCase,
    private val getAppPreferencesUseCase: GetAppPreferencesUseCase,
) : ViewModel() {

    init {
        analytics.logScreenView(AnalyticsEvent.Screen.ONBOARDING)
    }

    // ── STATE ─────────────────────────────────────────────────────────────────
    private val _state = MutableStateFlow(
        OnboardingState(
            currentStep = OnboardingStep.LANGUAGE,
            selectedLanguage = getAppPreferencesUseCase.getSync().language
        )
    )
    val state: StateFlow<OnboardingState> = _state.asStateFlow()

    // ── EFFECTS ───────────────────────────────────────────────────────────────
    // replay = 0 → never replays on resubscription (correct for navigation)
    private val _effects = MutableSharedFlow<OnboardingEffect>(replay = 0)
    val effect = _effects.asSharedFlow()

    val completedState: StateFlow<OnboardingCompleteState> = getOnboardingStatusUseCase()
        .map { completed ->
            if (completed == null) OnboardingCompleteState.Loading
            else if (completed) OnboardingCompleteState.Completed
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

            is OnboardingIntent.SelectLanguage -> {
                selectLanguage(appLanguage = intent.appLanguage)
            }

            OnboardingIntent.ConfirmLanguage -> {
                handleConfirmLanguage()
            }
            OnboardingIntent.BackToLanguage -> {
                handleBackToLanguage()
            }
            OnboardingIntent.BackToWelcome -> {
                handleBackToWelcome()
            }
        }
    }

    // ── HANDLERS ──────────────────────────────────────────────────────────────


    private fun selectLanguage(appLanguage: AppLanguage){
        _state.update { it.copy(selectedLanguage = appLanguage) }
        viewModelScope.launch {
            updateAppLanguageUseCase(appLanguage)
        }
    }

    private fun handleConfirmLanguage() {
        _state.update { it.copy(currentStep = OnboardingStep.WELCOME) }
    }

    private fun handleBackToLanguage(){
        _state.update { it.copy(currentStep = OnboardingStep.LANGUAGE) }
    }

    private fun handleBackToWelcome() {
        _state.update { it.copy(currentStep = OnboardingStep.WELCOME) }
    }

    private fun handleGeStarted() {
        analytics.logEvent(AnalyticsEvent.Event.ONBOARDING_STARTED)
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
            val result = seedStarterMaterialsUseCase.invoke(trade = _state.value.selectedTrades)

            when(result){
                is Result.Success -> {
                    _state.update { it.copy(totalMaterialCount = result.data) }

                    // 2. Write is_complete = true + selected trades to Datastore
                    completeOnboardingUseCase(trades = _state.value.selectedTrades)

                    // 3. update state
                    _state.update { it.copy(isLoading = false, seededMaterialCount = 1) }

                    // 4. fire navigation effect - clear back stack on Android,
                    // switches AppFeature.State on IOS

                    analytics.logEvent(AnalyticsEvent.Event.ONBOARDING_COMPLETED)
                    _effects.emit(OnboardingEffect.NavigationToDashboard)
                }
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, error = result.error.asString()) }
                }
            }
        }
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

}