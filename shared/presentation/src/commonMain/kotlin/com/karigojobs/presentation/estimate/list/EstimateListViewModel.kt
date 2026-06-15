package com.karigojobs.presentation.estimate.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.estimate.DeleteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetAllEstimateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


/**
 * @author hazratummar
 * Created on 09/06/26
 */

class EstimateListViewModel (
    private val getAllEstimateUseCase: GetAllEstimateUseCase,
    private val deleteEstimateUseCase: DeleteEstimateUseCase
): ViewModel() {

    private val _state = MutableStateFlow(EstimateListState())
    val state : StateFlow<EstimateListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EstimateListEffect>(replay = 0)
    val effect : SharedFlow<EstimateListEffect> = _effect.asSharedFlow()


    init {
        loadEstimate()
    }



    fun onEvent(event: EstimateListEvent) {
        when(event){
            is EstimateListEvent.SearchEstimate -> {
                _state.update { it.copy(estimateQuery = event.query) }
            }
            is EstimateListEvent.ToggleDelete -> {
                _state.update { it.copy(isDeleting = event.isOpen, workingEstimateId = event.id) }
            }

            is EstimateListEvent.DeleteEstimate -> {
                viewModelScope.launch {
                    val result = deleteEstimateUseCase(id = event.id)
                    _state.update { it.copy(isDeleting = false) }
                    when(result) {
                        is Result.Error -> {
                            _effect.emit(EstimateListEffect.ShowError(result.error.toString()))
                        }
                        is Result.Success<*> -> {
                            _state.update { it.copy(isDeleting = false) }
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun loadEstimate () {
        viewModelScope.launch {
            _state.map { it.estimateQuery }
                .distinctUntilChanged()
                .debounce(200.milliseconds)
                .flatMapLatest { query ->
                    _state.update { it.copy(isLoading = true) }
                    getAllEstimateUseCase(query = query)
                }.collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    when(result){
                        is Result.Error ->  {
                            _effect.emit(EstimateListEffect.ShowError(result.error.toString()))
                        }
                        is Result.Success -> {
                            _state.update { it.copy(estimates = result.data) }
                        }
                    }
                }
        }
    }

}