package com.karigojobs.domain.usecase.estimate

import com.karigojobs.domain.repository.SiteEstimateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel


/**
 * @author hazratummar
 * Created on 09/06/26
 */

class AddNewSiteEstimateUseCase(
    private val estimateRepository: SiteEstimateRepository
) {

    suspend operator fun invoke(siteEstimateModel: SiteEstimateModel, siteEstimateMaterials : List<SiteEstimateMaterial>) : Result<Unit, SiteEstimateError> {
        return estimateRepository.insertSiteEstimate(siteEstimateModel = siteEstimateModel, siteEstimateMaterials = siteEstimateMaterials)
    }

}