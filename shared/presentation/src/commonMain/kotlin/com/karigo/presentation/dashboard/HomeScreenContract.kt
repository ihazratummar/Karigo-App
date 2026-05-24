package com.karigo.presentation.dashboard

import com.karigo.share.model.JobModel
import com.karigo.share.model.TradeType


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
