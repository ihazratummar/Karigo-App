package com.karigojobs.presentation.dashboard

import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 23/05/26
 */


// ── STATE ─────────────────────────────────────────────────────────────────────

data class HomeState(
    val isLoading: Boolean =false,
    val selectedTrades: Set<TradeType> = emptySet(),
    val jobs: List<JobModel>? = emptyList()
)


// ── Effect ─────────────────────────────────────────────────────────────────────
sealed interface HomeEffect {
    data class ShowError(val message: String) : HomeEffect
}