package com.karigojobs.domain.repository

import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import kotlinx.coroutines.flow.Flow


/**
 * @author hazratummar
 * Created on 05/06/26
 */

interface SiteEstimateRepository {

    fun getAllSiteEstimates(query: String) : Flow<Result<List<SiteEstimateModel>, SiteEstimateError>>
    fun getSiteEstimate(id: String) : Flow<Result<SiteEstimateModel, SiteEstimateError>>

    suspend fun insertSiteEstimate(siteEstimateModel: SiteEstimateModel, siteEstimateMaterials : List<SiteEstimateMaterial>) : Result<Unit, SiteEstimateError>
    suspend fun updateSiteEstimate(siteEstimateModel: SiteEstimateModel, siteEstimateMaterial: List<SiteEstimateMaterial>) : Result<Unit, SiteEstimateError>



}