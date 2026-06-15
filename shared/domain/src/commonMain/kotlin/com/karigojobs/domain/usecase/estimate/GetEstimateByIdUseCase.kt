package com.karigojobs.domain.usecase.estimate

import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.share.model.SiteEstimateModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 15/06/26
 */

class GetEstimateByIdUseCase(
    private val estimateRepository: SiteEstimateRepository
) {

    operator fun invoke(estimateId: String) : Flow<Result<SiteEstimateModel, SiteEstimateError>> {
        return estimateRepository.getSiteEstimate(id = estimateId)
    }

}