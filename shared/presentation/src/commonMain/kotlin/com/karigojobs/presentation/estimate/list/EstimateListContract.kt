package com.karigojobs.presentation.estimate.list

import com.karigojobs.share.model.SiteEstimateModel


/**
 * @author hazratummar
 * Created on 09/06/26
 */
 


data class EstimateListState(
    val isLoading: Boolean = false,
    val estimates : List<SiteEstimateModel> = emptyList(),
    val isDeleting : Boolean = false,
    val estimateQuery : String = "",
    val workingEstimateId : String? = null
)


sealed interface EstimateListEvent {
    data class SearchEstimate (val query: String) : EstimateListEvent
    data class ToggleDelete (val isOpen : Boolean, val id: String ? = null) : EstimateListEvent
    data class DeleteEstimate(val id: String) : EstimateListEvent
}

sealed interface EstimateListEffect {
    data class ShowError(val message: String) : EstimateListEffect
}