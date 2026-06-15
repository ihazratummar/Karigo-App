package com.karigojobs.domain.usecase.estimate

import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError


/**
 * @author hazratummar
 * Created on 09/06/26
 */

class DeleteEstimateUseCase(
    private val estimateRepository: SiteEstimateRepository
){
    suspend operator fun invoke(id: String) : Result<Unit, SiteEstimateError> {
        return estimateRepository.deleteEstimate(estimateId = id)
    }
}