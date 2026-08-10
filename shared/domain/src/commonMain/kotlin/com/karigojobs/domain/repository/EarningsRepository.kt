package com.karigojobs.domain.repository

import com.karigojobs.domain.model.EarningsSummaryModel
import com.karigojobs.share.model.EarningsTimeframe
import kotlinx.coroutines.flow.Flow

interface EarningsRepository {
    fun getEarningsSummary(timeframe: EarningsTimeframe = EarningsTimeframe.SIX_MONTHS): Flow<EarningsSummaryModel>
}
