package com.karigojobs.presentation.onboarding

import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 21/05/26
 */


// ── STATE ─────────────────────────────────────────────────────────────────────
data class OnboardingState(
    val currentStep : OnboardingStep = OnboardingStep.LANGUAGE,
    val selectedTrades : Set<TradeType> = emptySet(),
    val isLoading: Boolean = false,
    val seededMaterialCount : Int = 0,
    val error: String ? = null,
    val totalMaterialCount: Int = 0,
    val selectedLanguage: AppLanguage = AppLanguage.ENGLISH
){
    val canContinue: Boolean get() = selectedTrades.isNotEmpty()
}

// ── STEP ──────────────────────────────────────────────────────────────────────

enum class OnboardingStep {
    LANGUAGE, WELCOME, TRADE_SELECT, READY
}


sealed interface OnboardingCompleteState {
    data object Loading : OnboardingCompleteState
    data object Completed : OnboardingCompleteState
    data object NotCompleted : OnboardingCompleteState
}

// ── INTENT ────────────────────────────────────────────────────────────────────

sealed class OnboardingIntent {

    data class SelectLanguage(val appLanguage: AppLanguage): OnboardingIntent()
    data object ConfirmLanguage : OnboardingIntent()

    // Welcome screen
    data object GetStarted : OnboardingIntent()
    data object BackToLanguage : OnboardingIntent()

    // Trade select screen
    data class ToggleTrade(val trade : TradeType) : OnboardingIntent()
    data object ConfirmTrades : OnboardingIntent()
    data object BackToWelcome : OnboardingIntent()

    // Ready Screen
    data object LetsGo : OnboardingIntent()
    data object BackToTrades : OnboardingIntent()

    // Error Handling
    data object DismissError: OnboardingIntent()
}

// ── EFFECT ────────────────────────────────────────────────────────────────────
// One-shot events — navigation, snackbar, haptic
// These are NOT stored in state — they fire once and are consumed

sealed class OnboardingEffect {
    data object NavigationToDashboard : OnboardingEffect()
    data class ShowError(val message: String) : OnboardingEffect()
}