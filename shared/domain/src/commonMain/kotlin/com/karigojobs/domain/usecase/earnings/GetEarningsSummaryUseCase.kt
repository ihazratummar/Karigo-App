package com.karigojobs.domain.usecase.earnings

import com.karigojobs.domain.model.EarningsSummaryModel
import com.karigojobs.domain.repository.EarningsRepository
import com.karigojobs.share.model.EarningsTimeframe
import kotlinx.coroutines.flow.Flow

class GetEarningsSummaryUseCase(
    private val earningsRepository: EarningsRepository
) {
    operator fun invoke(timeframe: EarningsTimeframe = EarningsTimeframe.SIX_MONTHS): Flow<EarningsSummaryModel> {
        return earningsRepository.getEarningsSummary(timeframe)
    }
}
