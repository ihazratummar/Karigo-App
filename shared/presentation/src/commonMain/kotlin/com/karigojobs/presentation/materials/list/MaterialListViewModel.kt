package com.karigojobs.presentation.materials.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.material.DeleteMaterialUseCase
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


/**
 * @author hazratummar
 * Created on 04/06/26
 */

class MaterialListViewModel(
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val searchMaterialsUseCase: SearchMaterialsUseCase,
    private val deleteMaterialUseCase: DeleteMaterialUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(MaterialListState())
    val state : StateFlow<MaterialListState> = _state.asStateFlow()


    init {
        loadSelectedTrades()
        loadMaterials()
    }

    fun onEvent(event: MaterialListEvent) {
        when (event) {
            is MaterialListEvent.DeleteMaterial -> {
               viewModelScope.launch {
                   val result = deleteMaterialUseCase.invoke(materialId = event.materialId)
                   when(result){
                       is Result.Error -> TODO()
                       is Result.Success ->  {
                           _state.update { it.copy(isDeleting = false) }
                       }
                   }
               }
            }
            is MaterialListEvent.SearchMaterial -> {
                _state.update { it.copy(materialQuery = event.query) }
            }
            is MaterialListEvent.SelectTradeType -> {
                _state.update {
                    it.copy(
                        selectedTradeType = event.tradeType,
                        materialFilter = if (event.tradeType == null) MaterialListFilter.All
                        else MaterialListFilter.SelectedTrade(setOf(event.tradeType))
                    )
                }
            }

            is MaterialListEvent.EditMaterial -> {
                TODO()
            }
            is MaterialListEvent.ToggleDeleteMaterialClick -> {
                _state.update { it.copy(isDeleting = event.isDeleting, deletingMaterialId = event.materialId) }
            }
        }
    }

    private fun loadSelectedTrades() {
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when(result){
                    is Result.Error -> TODO()
                    is Result.Success ->  {
                        _state.update {
                            it.copy(
                                selectTrades = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun loadMaterials() {
        viewModelScope.launch {
            combine(
                _state.map { it.materialQuery }.distinctUntilChanged(),
                _state.map { it.materialFilter }.distinctUntilChanged()
            ) { query, filter -> query to filter }
                .debounce(300.milliseconds)
                .flatMapLatest { (query, filter) ->
                    _state.update { it.copy(isLoading = true) }
                    val tradeTypes = when (filter) {
                        is MaterialListFilter.All -> null
                        is MaterialListFilter.SelectedTrade -> filter.selectedTrade
                    }
                    searchMaterialsUseCase(query = query, tradeTypes = tradeTypes)
                }.collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    when (result) {
                        is Result.Error -> TODO()
                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    materialsList = result.data
                                )
                            }
                        }
                    }
                }
        }
    }
}