package com.karigojobs.presentation.dashboard


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.job.GetAllJobUseCase
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class HomeViewModel(
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val getAllJobUseCase: GetAllJobUseCase,
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {


    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<HomeEffect>(replay = 0)
    val effect: SharedFlow<HomeEffect> = _effect.asSharedFlow()


    init {
        analytics.logScreenView(AnalyticsEvent.Screen.HOME)
        loadSelectedTrade()
        loadAllJob()
        loadWorker()
    }


    private fun loadAllJob() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getAllJobUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(jobs = result.data, isLoading = false) }
                        println("Jobs Data -> $result.data")
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(HomeEffect.ShowError(message = result.error.asString()))
                    }
                }
            }
        }
    }

    private fun loadSelectedTrade() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                selectedTrades = result.data
                            )
                        }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _effect.emit(HomeEffect.ShowError(message = result.error.toString()))
                    }
                }
            }
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
}