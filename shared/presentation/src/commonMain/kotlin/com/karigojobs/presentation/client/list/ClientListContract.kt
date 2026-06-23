package com.karigojobs.presentation.client.list

import com.karigojobs.share.model.ClientModel


/**
 * @author hazratummar
 * Created on 23/06/26
 */
 


data class  ClientListState(
    val isLoading : Boolean = false,
    val clients : List<ClientModel> = emptyList(),
    val totalOutStanding : Double = 0.0,
    val searchClient: String = "",
)

sealed interface ClientListEvent {
    data class SearchClient(val query: String) : ClientListEvent
}

sealed interface ClientListEffect {
    data class ShowError (val message: String) : ClientListEffect
}