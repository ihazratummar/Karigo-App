package com.karigojobs.feature.settings.earnings

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.karigojobs.presentation.earnings.EarningsEffect
import com.karigojobs.presentation.earnings.EarningsEvent
import com.karigojobs.presentation.earnings.EarningsState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun EarningsScreen(
    state: EarningsState,
    event: (EarningsEvent) -> Unit,
    effect: SharedFlow<EarningsEffect>,
    onNavigateBack: () -> Unit,
    onNavigateToPaywall: () -> Unit,
    modifier: Modifier = Modifier
) {
    LaunchedEffect(effect) {
        effect.collectLatest { eff ->
            when (eff) {
                EarningsEffect.NavigateBack -> onNavigateBack()
                EarningsEffect.NavigateToPaywall -> onNavigateToPaywall()
            }
        }
    }

    if (state.isPro) {
        EarningsDashboardScreen(
            state = state,
            event = event,
            modifier = modifier
        )
    } else {
        EarningsPaywallScreen(
            state = state,
            event = event,
            modifier = modifier
        )
    }
}
