package com.karigojobs.presentation.client.details

import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobModel


/**
 * @author hazratummar
 * Created on 23/06/26
 */
 


data class ClientDetailsState(
    val isLoading : Boolean = false,
    val client : ClientModel? = null,
    val jobHistory : List<JobModel> = emptyList(),
    val isDeleting : Boolean = false
)

sealed interface ClientDetailsEvent {
    data class ToggleDelete(val isOpen : Boolean) : ClientDetailsEvent
    data object DeleteClient : ClientDetailsEvent
    data object CallClient : ClientDetailsEvent
    data object OpenClientWhatsApp  : ClientDetailsEvent
}

sealed interface ClientDetailsEffect {

    data class ShowError(val message : String) : ClientDetailsEffect
    data class CallClient(val number: String) : ClientDetailsEffect
    data class OpenWhatsApp(val number: String) : ClientDetailsEffect

    data object NavBack : ClientDetailsEffect
}