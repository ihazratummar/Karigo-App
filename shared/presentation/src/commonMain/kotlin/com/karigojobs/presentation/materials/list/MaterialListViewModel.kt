package com.karigojobs.presentation.materials.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.material.DeleteMaterialUseCase
import com.karigojobs.domain.usecase.material.GetMaterialByIdUseCase
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import com.karigojobs.domain.usecase.material.UpdateMaterialUseCase
import com.karigojobs.presentation.materials.list.MaterialListFilter.*
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
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
    private val deleteMaterialUseCase: DeleteMaterialUseCase,
    private val getMaterialByIdUseCase: GetMaterialByIdUseCase,
    private val updateMaterialUseCase: UpdateMaterialUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(MaterialListState())
    val state: StateFlow<MaterialListState> = _state.asStateFlow()

    private val editingMaterialId = MutableStateFlow<String?>(null)


    init {
        loadSelectedTrades()
        observeEditingMaterial()
        loadMaterials()
    }

    fun onEvent(event: MaterialListEvent) {
        when (event) {
            is MaterialListEvent.DeleteMaterial -> {
                viewModelScope.launch {
                    val result = deleteMaterialUseCase.invoke(materialId = event.materialId)
                    when (result) {
                        is Result.Error -> {}
                        is Result.Success -> {
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
                        materialFilter = if (event.tradeType == null) All
                        else SelectedTrade(setOf(event.tradeType))
                    )
                }
            }

            is MaterialListEvent.EditMaterial -> {

            }

            is MaterialListEvent.ToggleDeleteMaterialClick -> {
                _state.update {
                    it.copy(
                        isDeleting = event.isDeleting,
                        workingMaterialId = event.materialId
                    )
                }
            }

            is MaterialListEvent.ToggleEditMaterialModal -> {
                _state.update {
                    it.copy(
                        isEditMaterialModalOpen = event.isEditing,
                        workingMaterialId = event.materialId
                    )
                }

                if (event.materialId != null) {
                    editingMaterialId.value = event.materialId
                } else {
                    editingMaterialId.value = null
                }
            }

            is MaterialListEvent.EditMaterialName -> {
                _state.update {
                    it.copy(
                        editingMaterial = it.editingMaterial?.copy(name = event.name)
                    )
                }
            }

            is MaterialListEvent.EditMaterialPrice -> {
                _state.update {
                    it.copy(
                        editingMaterial = it.editingMaterial?.copy(price = event.price.toDouble())
                    )
                }
            }
            is MaterialListEvent.EditMaterialUnit -> {
                _state.update {
                    it.copy(
                        editingMaterial = it.editingMaterial?.copy(unit = event.unit)
                    )
                }
            }

            is MaterialListEvent.UpdateMaterials -> {
                viewModelScope.launch {
                    if (editingMaterialId.value != null) {

                        val editMaterial = _state.value.editingMaterial
                        if (editMaterial != null){
                            val result = updateMaterialUseCase(
                                material = MaterialsModel(
                                    name = editMaterial.name,
                                    unit = editMaterial.unit,
                                    price = editMaterial.price,
                                    tradeType = editMaterial.tradeType,
                                    id = editMaterial.id
                                )
                            )

                            when(result){
                                is Result.Error -> {}
                                is Result.Success -> {
                                    _state.update { it.copy(isLoading = false, isEditMaterialModalOpen = false) }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeEditingMaterial() {
        editingMaterialId
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapLatest { materialId ->
                flow {
                    emit(
                        getMaterialByIdUseCase(materialId)
                    )
                }
            }
            .onEach { result ->
                when (result) {
                    is Result.Error -> {}
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                editingMaterial = result.data
                            )
                        }
                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun loadSelectedTrades() {
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Error -> {}
                    is Result.Success -> {
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
                        is All -> null
                        is SelectedTrade -> filter.selectedTrade
                    }
                    searchMaterialsUseCase(query = query, tradeTypes = tradeTypes)
                }.collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    when (result) {
                        is Result.Error -> {}
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