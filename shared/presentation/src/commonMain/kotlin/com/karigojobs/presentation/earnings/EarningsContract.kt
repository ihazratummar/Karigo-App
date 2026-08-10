package com.karigojobs.presentation.earnings

import com.karigojobs.domain.model.EarningsSummaryModel
import com.karigojobs.share.model.EarningsTimeframe
import com.karigojobs.share.model.PlanTier

data class EarningsState(
    val isPro: Boolean = false,
    val isLoading: Boolean = false,
    val selectedPlanTier: PlanTier = PlanTier.PRO_LIFETIME,
    val selectedTimeframe: EarningsTimeframe = EarningsTimeframe.SIX_MONTHS,
    val summary: EarningsSummaryModel? = null
)

sealed interface EarningsEvent {
    data class SelectPlan(val planTier: PlanTier) : EarningsEvent
    data class SelectTimeframe(val timeframe: EarningsTimeframe) : EarningsEvent
    data object UnlockProClick : EarningsEvent
    data object BackClick : EarningsEvent
}

sealed interface EarningsEffect {
    data object NavigateBack : EarningsEffect
    data object NavigateToPaywall : EarningsEffect
}
