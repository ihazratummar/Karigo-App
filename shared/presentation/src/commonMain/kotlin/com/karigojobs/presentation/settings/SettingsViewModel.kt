package com.karigojobs.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.domain.usecase.settings.UpdateAppLanguageUseCase
import com.karigojobs.domain.usecase.settings.UpdateThemePreferenceUseCase
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.trade.SaveTradesUseCase
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class SettingsViewModel (
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase,
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val saveTradesUseCase: SaveTradesUseCase,
    private val getAppPreferencesUseCase: GetAppPreferencesUseCase,
    private val updateThemePreferenceUseCase: UpdateThemePreferenceUseCase,
    private val updateAppLanguageUseCase: UpdateAppLanguageUseCase
): ViewModel() {


    private val _state = MutableStateFlow(
        SettingsState(
            workerProfileModel = getWorkerProfileUseCase.invokeSync(),
            selectedTrades = getSelectedTradeTypeUseCase.getSync(),
            editTrades = getSelectedTradeTypeUseCase.getSync(),
            currentTheme = getAppPreferencesUseCase.getSync().theme,
            currentLanguage = getAppPreferencesUseCase.getSync().language
        )
    )
    val state : StateFlow<SettingsState> = _state.asStateFlow()

    init {
        loadWorker()
        loadSelectedTradeTypes()
        loadAppPreferences()
    }


    fun onEvent(event: SettingsEvent) {
        when(event) {
            is SettingsEvent.EditTrade -> {
                handleEditTrade(tradeType = event.trade)
            }
            is SettingsEvent.ToggleTradeSelectModal -> {
                _state.update { it.copy(isTradeSelectModalOpen = event.isOpen) }
            }

            SettingsEvent.ClearAllTrade -> {
                _state.update { it.copy(selectedTrades = emptySet()) }
            }

            SettingsEvent.SaveTrades -> {
                viewModelScope.launch {
                     saveTradesUseCase(trades = _state.value.editTrades)
                    _state.update { it.copy(isTradeSelectModalOpen = false) }
                }
            }
            is SettingsEvent.ToggleLanguageModal -> {
                _state.update { it.copy(isLanguageModalOpen = event.isOpen) }
            }
            is SettingsEvent.ToggleThemeModal -> {
                _state.update { it.copy(isThemeModalOpen = event.isOpen) }
            }
            is SettingsEvent.UpdateLanguage -> {
                viewModelScope.launch {
                    updateAppLanguageUseCase(event.language)
                    _state.update { it.copy(isLanguageModalOpen = false) }
                }
            }
            is SettingsEvent.UpdateTheme -> {
                viewModelScope.launch {
                    updateThemePreferenceUseCase(event.theme)
                    _state.update { it.copy(isThemeModalOpen = false) }
                }
            }
        }
    }

    private fun handleEditTrade(tradeType: TradeType) {
        _state.update { current ->
            val updateTrades = current.editTrades.toMutableSet()
            if (updateTrades.contains(tradeType)){
                updateTrades.remove(tradeType)
            }else{
                updateTrades.add(tradeType)
            }
            current.copy(editTrades = updateTrades)
        }
    }

    private fun loadWorker() {
        viewModelScope.launch {
            getWorkerProfileUseCase().collectLatest { result ->
                when(result){
                    is Result.Error -> {}
                    is Result.Success -> {
                        _state.update { it.copy(workerProfileModel = result.data) }
                    }
                }
            }
        }
    }

    private fun loadSelectedTradeTypes() {
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when(result){
                    is Result.Error -> {}
                    is Result.Success -> {
                        _state.update { it.copy(selectedTrades = result.data, editTrades = result.data) }
                    }
                }
            }
        }
    }

    private fun loadAppPreferences() {
        viewModelScope.launch {
            getAppPreferencesUseCase().collectLatest { prefs ->
                _state.update { it.copy(currentTheme = prefs.theme, currentLanguage = prefs.language) }
            }
        }
    }
}