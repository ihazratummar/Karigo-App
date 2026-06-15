package com.karigojobs.presentation.estimate.details

import com.karigojobs.presentation.estimate.list.EstimateListEvent
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel


/**
 * @author hazratummar
 * Created on 15/06/26
 */
 


data class EstimateDetailsState(
    val isLoading : Boolean = false,
    val estimateDetails : SiteEstimateModel? = null,
    val siteEstimateMaterial: List<SiteEstimateMaterial> = emptyList(),
    val isDeleting: Boolean = false,
)



sealed interface EstimateDetailsEvent{

    data class DeleteEstimate(val estimateId: String) : EstimateDetailsEvent
    data object WhatsAppShare : EstimateDetailsEvent
    data class ToggleDelete (val isOpen : Boolean, ) : EstimateDetailsEvent

}

sealed interface EstimateDetailsEffect {
    data class ShowError(val message: String) : EstimateDetailsEffect
    data class ShareToWhatsApp(val message: String) : EstimateDetailsEffect
    data object NavigationBack : EstimateDetailsEffect
}