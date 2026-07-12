package com.karigojobs.presentation.estimate.details

import com.karigojobs.presentation.estimate.list.EstimateListEvent
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 15/06/26
 */
 


data class EstimateDetailsState(
    val isLoading : Boolean = false,
    val estimateDetails : SiteEstimateModel? = null,
    val siteEstimateMaterial: List<SiteEstimateMaterial> = emptyList(),
    val isDeleting: Boolean = false,
    val workerProfileModel : WorkerProfileModel? = null,
    val clientModel : ClientModel? = null
)



sealed interface EstimateDetailsEvent{

    data class DeleteEstimate(val estimateId: String) : EstimateDetailsEvent
    data object WhatsAppShare : EstimateDetailsEvent
    data class ToggleDelete (val isOpen : Boolean, ) : EstimateDetailsEvent
    data class GenerateEstimatePdf(val currencySymbol: String) : EstimateDetailsEvent
    data class ShareEstimateOnWhatsapp(val currencySymbol: String) : EstimateDetailsEvent

}

sealed interface EstimateDetailsEffect {
    data class ShowError(val message: String) : EstimateDetailsEffect
    data class ShareToWhatsApp(val message: String) : EstimateDetailsEffect
    data object NavigationBack : EstimateDetailsEffect
    data class ShareEstimatePdf(val html: String, val estimateTitle: String) : EstimateDetailsEffect
    data class ShareTextOnWhatsapp(val text: String) : EstimateDetailsEffect
}