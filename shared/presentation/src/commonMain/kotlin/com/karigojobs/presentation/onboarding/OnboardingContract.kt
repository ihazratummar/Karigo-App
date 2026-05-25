package com.karigojobs.presentation.onboarding

import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 21/05/26
 */


// ── STATE ─────────────────────────────────────────────────────────────────────
data class OnboardingState(
    val currentStep : OnboardingStep = OnboardingStep.WELCOME,
    val selectedTrades : Set<TradeType> = emptySet(),
    val isLoading: Boolean = false,
    val seededMaterialCount : Int = 0,
    val error: String ? = null,
    val totalMaterialCount: Int = 0
){
    val canContinue: Boolean get() = selectedTrades.isNotEmpty()
    val continueLabel: String get() = when {
        selectedTrades.isEmpty() -> "Select at least one trade"
        selectedTrades.size == 1 -> "Continue (1 selected)"
        else -> "Continue (${selectedTrades.size} selected)"
    }
}

// ── STEP ──────────────────────────────────────────────────────────────────────

enum class OnboardingStep {
    WELCOME, TRADE_SELECT, READY
}


sealed interface OnboardingCompleteState {
    data object Loading : OnboardingCompleteState
    data object Completed : OnboardingCompleteState
    data object NotCompleted : OnboardingCompleteState
}

// ── INTENT ────────────────────────────────────────────────────────────────────

sealed class OnboardingIntent {
    // Welcome screen
    data object GetStarted : OnboardingIntent()

    // Trade select screen
    data class ToggleTrade(val trade : TradeType) : OnboardingIntent()
    data object ConfirmTrades : OnboardingIntent()

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