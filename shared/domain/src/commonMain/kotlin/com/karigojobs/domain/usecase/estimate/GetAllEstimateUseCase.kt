package com.karigojobs.domain.usecase.estimate

import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.share.model.SiteEstimateModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 09/06/26
 */

class GetAllEstimateUseCase(
    private val estimateRepository: SiteEstimateRepository
) {

    operator fun invoke(query: String) : Flow<Result<List<SiteEstimateModel>, SiteEstimateError>> {
        return estimateRepository.getAllSiteEstimates(query = query)
    }

}