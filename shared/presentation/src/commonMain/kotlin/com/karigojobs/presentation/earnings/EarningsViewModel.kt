package com.karigojobs.presentation.earnings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.usecase.earnings.GetEarningsSummaryUseCase
import com.karigojobs.domain.usecase.monetization.ObserveProStatusUseCase
import com.karigojobs.share.model.EarningsTimeframe
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EarningsViewModel(
    private val observeProStatusUseCase: ObserveProStatusUseCase,
    private val getEarningsSummaryUseCase: GetEarningsSummaryUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(EarningsState())
    val state: StateFlow<EarningsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EarningsEffect>()
    val effect: SharedFlow<EarningsEffect> = _effect.asSharedFlow()

    init {
        observeProStatus()
        observeEarningsSummary()
    }

    fun onEvent(event: EarningsEvent) {
        when (event) {
            is EarningsEvent.SelectPlan -> {
                _state.update { it.copy(selectedPlanTier = event.planTier) }
            }

            is EarningsEvent.SelectTimeframe -> {
                _state.update { it.copy(selectedTimeframe = event.timeframe) }
                observeEarningsSummary(event.timeframe)
            }

            EarningsEvent.UnlockProClick -> {
                viewModelScope.launch {
                    _effect.emit(EarningsEffect.NavigateToPaywall)
                }
            }

            EarningsEvent.BackClick -> {
                viewModelScope.launch {
                    _effect.emit(EarningsEffect.NavigateBack)
                }
            }
        }
    }

    private fun observeProStatus() {
        viewModelScope.launch {
            observeProStatusUseCase().collectLatest { proStatus ->
                _state.update { it.copy(isPro = proStatus.hasProAccess) }
            }
        }
    }

    private fun observeEarningsSummary(timeframe: EarningsTimeframe = _state.value.selectedTimeframe) {
        viewModelScope.launch {
            getEarningsSummaryUseCase(timeframe).collectLatest { summary ->
                _state.update { it.copy(summary = summary) }
            }
        }
    }
}
