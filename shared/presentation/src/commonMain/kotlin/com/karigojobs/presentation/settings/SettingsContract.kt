package com.karigojobs.presentation.settings

import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
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
    val editTrades : Set<TradeType> = emptySet(),
    val currentTheme: ThemePreference = ThemePreference.SYSTEM,
    val currentLanguage: AppLanguage = AppLanguage.ENGLISH,
    val currentCurrency: String = "₹",
    val isThemeModalOpen: Boolean = false,
    val isLanguageModalOpen: Boolean = false,
    val isCurrencyModalOpen: Boolean = false,
    val isPreviewProEnable: Boolean = false,
    val proStatus: com.karigojobs.share.model.ProStatus = com.karigojobs.share.model.ProStatus()
){
    val selectedCount : Int get() = editTrades.count()
}

sealed interface SettingsEvent {
    data class EditTrade(val trade: TradeType) : SettingsEvent
    data class ToggleTradeSelectModal(val isOpen  : Boolean) : SettingsEvent
    data object ClearAllTrade : SettingsEvent
    data object SaveTrades : SettingsEvent

    data class ToggleThemeModal(val isOpen: Boolean) : SettingsEvent
    data class ToggleLanguageModal(val isOpen: Boolean) : SettingsEvent
    data class ToggleCurrencyModal(val isOpen: Boolean) : SettingsEvent
    data class UpdateTheme(val theme: ThemePreference) : SettingsEvent
    data class UpdateLanguage(val language: AppLanguage) : SettingsEvent
    data class UpdateCurrency(val currency: String) : SettingsEvent

    data object PreviewPro  : SettingsEvent
}

sealed interface SettingsEffect {
    data class ShowError(val message : String) : SettingsEffect
}