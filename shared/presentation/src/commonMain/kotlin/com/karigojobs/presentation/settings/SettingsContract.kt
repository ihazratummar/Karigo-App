package com.karigojobs.presentation.settings

import com.karigojobs.share.model.TradeType
import com.karigojobs.share.model.WorkerProfileModel


/**
 * @author hazratummar
 * Created on 24/06/26
 */


data class SettingsState(
    val isLoading: Boolean = false,
    val workerProfileModel: WorkerProfileModel? = null,
    val selectedTrades: Set<TradeType>? = null,
    val isTradeSelectModalOpen: Boolean = false,
    val editTrades : Set<TradeType> = emptySet()
){
    val selectedCount : Int get() = editTrades.count()
}

sealed interface SettingsEvent {
    data class EditTrade(val trade: TradeType) : SettingsEvent
    data class ToggleTradeSelectModal(val isOpen  : Boolean) : SettingsEvent
    data object ClearAllTrade : SettingsEvent

    data object SaveTrades : SettingsEvent
}

sealed interface SettingsEffect {
    data class ShowError(val message : String) : SettingsEffect
}