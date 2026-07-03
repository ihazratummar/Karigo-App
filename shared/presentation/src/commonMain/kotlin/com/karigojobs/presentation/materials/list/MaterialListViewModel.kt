package com.karigojobs.presentation.materials.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.material.AddMaterialUseCase
import com.karigojobs.domain.usecase.material.DeleteMaterialUseCase
import com.karigojobs.domain.usecase.material.GetMaterialByIdUseCase
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import com.karigojobs.domain.usecase.material.UpdateMaterialUseCase
import com.karigojobs.domain.usecase.materialCategory.GetMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.InsertMaterialCategoryUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.materials.list.MaterialScreenEffect.*
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
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
import kotlin.uuid.Uuid


/**
 * @author hazratummar
 * Created on 04/06/26
 */

class MaterialListViewModel(
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val searchMaterialsUseCase: SearchMaterialsUseCase,
    private val deleteMaterialUseCase: DeleteMaterialUseCase,
    private val getMaterialByIdUseCase: GetMaterialByIdUseCase,
    private val updateMaterialUseCase: UpdateMaterialUseCase,
    private val addMaterialUseCase: AddMaterialUseCase,
    private val getMaterialCategoryUseCase: GetMaterialCategoryUseCase,
    private val insertMaterialCategoryUseCase: InsertMaterialCategoryUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(MaterialListState())
    val state: StateFlow<MaterialListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MaterialScreenEffect>(replay = 0)
    val effect: SharedFlow<MaterialScreenEffect> = _effect.asSharedFlow()

    private val editingMaterialId = MutableStateFlow<String?>(null)


    init {
        loadSelectedTrades()
        observeEditingMaterial()
        observeActiveCategoryLoading()
        loadMaterials()
    }

    fun onEvent(event: MaterialListEvent) {
        when (event) {
            is MaterialListEvent.DeleteMaterial -> deleteMaterial(event.materialId)
            is MaterialListEvent.SearchMaterial -> _state.update { it.copy(materialQuery = event.query) }
            is MaterialListEvent.SelectTradeType -> {
                _state.update {
                    it.copy(
                        selectedTradeType = event.tradeType,
                        materialFilter = if (event.tradeType == null) MaterialListFilter.All
                        else MaterialListFilter.SelectedTrade(setOf(event.tradeType)),
                        selectedCategory = if (event.tradeType != it.selectedTradeType) null else it.selectedCategory
                    )
                }
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
                        workingMaterialId = event.materialId,
                        editingMaterial = if (!event.isEditing) null else it.editingMaterial
                    )
                }
                editingMaterialId.value = event.materialId
            }
            is MaterialListEvent.EditMaterialName -> {
                _state.update { it.copy(editingMaterial = it.editingMaterial?.copy(name = event.name)) }
            }
            is MaterialListEvent.EditMaterialPrice -> {
                _state.update { it.copy(editingMaterial = it.editingMaterial?.copy(price = event.price.toDoubleOrNull() ?: 0.0)) }
            }
            is MaterialListEvent.EditMaterialUnit -> {
                _state.update { it.copy(editingMaterial = it.editingMaterial?.copy(unit = event.unit)) }
            }
            is MaterialListEvent.EditMaterialCategoryName -> {
                _state.update { it.copy(editingMaterial = it.editingMaterial?.copy(categoryName = event.name)) }
            }
            is MaterialListEvent.EditMaterialTradeType -> {
                _state.update { it.copy(editingMaterial = it.editingMaterial?.copy(tradeType = event.tradeType)) }
            }
            is MaterialListEvent.UpdateMaterials -> updateMaterial()
            is MaterialListEvent.NewMaterialTradeType -> {
                _state.update { it.copy(newMaterialTradeType = event.tradeType) }
            }
            is MaterialListEvent.ToggleAddMaterialModal -> {
                _state.update {
                    it.copy(
                        isNewMaterialAddingModalOpen = event.isOpen,
                        newMaterialTradeType = if (event.isOpen) {
                            it.selectedTradeType ?: it.selectTrades.firstOrNull() ?: TradeType.ELECTRICIAN
                        } else null,
                        newMaterialName = if (!event.isOpen) "" else it.newMaterialName,
                        newMaterialPrice = if (!event.isOpen) "" else it.newMaterialPrice,
                        newMaterialUnit = if (!event.isOpen) "" else it.newMaterialUnit,
                        newMaterialCategoryName = if (!event.isOpen) "" else it.newMaterialCategoryName
                    )
                }
            }
            is MaterialListEvent.AddMaterial -> addMaterial()
            is MaterialListEvent.NewMaterialName -> {
                _state.update { it.copy(newMaterialName = event.name) }
            }
            is MaterialListEvent.NewMaterialRate -> {
                _state.update { it.copy(newMaterialPrice = event.rate) }
            }
            is MaterialListEvent.NewMaterialUnit -> {
                _state.update { it.copy(newMaterialUnit = event.unit) }
            }
            is MaterialListEvent.NewMaterialCategoryName -> {
                _state.update { it.copy(newMaterialCategoryName = event.name) }
            }
            is MaterialListEvent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }
        }
    }

    private suspend fun resolveCategoryId(tradeType: TradeType?, typedCategoryName: String?): Pair<Boolean, String?> {
        val trimmedName = typedCategoryName?.trim()
        if (trimmedName.isNullOrBlank()) return Pair(true, null)

        val existingCategory = _state.value.materialCategory.find {
            it.name.equals(trimmedName, ignoreCase = true)
        }
        if (existingCategory != null) return Pair(true, existingCategory.id)

        val newCategoryId = Uuid.random().toString()
        val newCategory = MaterialCategoryModel(
            id = newCategoryId,
            name = trimmedName,
            tradeType = tradeType ?: TradeType.ELECTRICIAN
        )
        
        val insertResult = insertMaterialCategoryUseCase(newCategory)
        if (insertResult is Result.Error) {
            _effect.emit(ShowError(insertResult.error.asString()))
            return Pair(false, null)
        }
        return Pair(true, newCategoryId)
    }

    private fun addMaterial() {
        _state.update { it.copy(isAdding = true) }
        viewModelScope.launch {
            val tradeTypeToSave = _state.value.newMaterialTradeType ?: TradeType.ELECTRICIAN
            val typedCategoryName = _state.value.newMaterialCategoryName

            val (isCategorySuccess, categoryIdToSave) = resolveCategoryId(tradeTypeToSave, typedCategoryName)
            if (!isCategorySuccess) {
                _state.update { it.copy(isAdding = false) }
                return@launch
            }

            val result = addMaterialUseCase(
                material = MaterialsModel(
                    name = _state.value.newMaterialName,
                    unit = _state.value.newMaterialUnit,
                    price = _state.value.newMaterialPrice.toDoubleOrNull() ?: 0.0,
                    tradeType = tradeTypeToSave,
                    categoryId = categoryIdToSave
                )
            )

            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(isNewMaterialAddingModalOpen = false, isAdding = false) }
                    _effect.emit(ShowError(result.error.asString()))
                }
                is Result.Success -> {
                    _state.update {
                        it.copy(
                            isNewMaterialAddingModalOpen = false,
                            isAdding = false,
                            newMaterialUnit = "",
                            newMaterialPrice = "",
                            newMaterialName = ""
                        )
                    }
                }
            }
        }
    }

    private fun updateMaterial() {
        viewModelScope.launch {
            val editMaterial = _state.value.editingMaterial ?: return@launch
            if (editingMaterialId.value == null) return@launch

            val (isCategorySuccess, categoryIdToSave) = resolveCategoryId(editMaterial.tradeType, editMaterial.categoryName)
            if (!isCategorySuccess) {
                _state.update { it.copy(isLoading = false, isEditMaterialModalOpen = false) }
                return@launch
            }

            val result = updateMaterialUseCase(
                material = MaterialsModel(
                    name = editMaterial.name,
                    unit = editMaterial.unit,
                    price = editMaterial.price,
                    tradeType = editMaterial.tradeType,
                    id = editMaterial.id,
                    categoryId = categoryIdToSave
                )
            )

            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(isLoading = false, isEditMaterialModalOpen = false) }
                    _effect.emit(ShowError(result.error.asString()))
                }
                is Result.Success -> {
                    _state.update { it.copy(isLoading = false, isEditMaterialModalOpen = false) }
                }
            }
        }
    }

    private fun deleteMaterial(materialId: String) {
        viewModelScope.launch {
            val result = deleteMaterialUseCase.invoke(materialId = materialId)
            when (result) {
                is Result.Error -> {
                    _state.update { it.copy(isDeleting = false) }
                    _effect.emit(ShowError(result.error.asString()))
                }
                is Result.Success -> {
                    _state.update { it.copy(isDeleting = false) }
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
                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false
                            )
                        }
                        _effect.emit(ShowError(result.error.asString()))
                    }

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

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeActiveCategoryLoading() {
        viewModelScope.launch {
            combine(
                _state.map { it.selectedTradeType }.distinctUntilChanged(),
                _state.map { it.isEditMaterialModalOpen }.distinctUntilChanged(),
                _state.map { it.editingMaterial?.tradeType }.distinctUntilChanged(),
                _state.map { it.isNewMaterialAddingModalOpen }.distinctUntilChanged(),
                _state.map { it.newMaterialTradeType }.distinctUntilChanged()
            ) { selectedTradeType, isEditOpen, editTradeType, isAddOpen, addTradeType ->
                when {
                    isEditOpen -> editTradeType
                    isAddOpen -> addTradeType
                    else -> selectedTradeType
                }
            }
                .distinctUntilChanged()
                .flatMapLatest { tradeType ->
                    getMaterialCategoryUseCase(tradeType)
                }
                .collectLatest { result ->
                    when (result) {
                        is Result.Error -> {
                            _effect.emit(ShowError(result.error.asString()))
                        }

                        is Result.Success -> {
                            _state.update { it.copy(materialCategory = result.data) }
                        }
                    }
                }
        }
    }

    private fun loadSelectedTrades() {
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Error -> {

                        _effect.emit(ShowError(result.error.asString()))

                    }

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
                _state.map { it.materialFilter }.distinctUntilChanged(),
                _state.map { it.selectedCategory }.distinctUntilChanged()
            ) { query, filter, category -> Triple(query, filter, category) }
                .debounce(300.milliseconds)
                .flatMapLatest { (query, filter, category) ->
                    _state.update { it.copy(isLoading = true) }
                    val tradeTypes = when (filter) {
                        is MaterialListFilter.All -> null
                        is MaterialListFilter.SelectedTrade -> filter.selectedTrade
                    }
                    val queryCategoryId =
                        if (category?.id == "uncategorized" || filter == MaterialListFilter.All) null else category?.id
                    searchMaterialsUseCase(
                        query = query,
                        tradeTypes = tradeTypes,
                        categoryId = queryCategoryId
                    ).map { result ->
                        if (category?.id == "uncategorized" && result is Result.Success) {
                            Result.Success(result.data.filter { it.categoryId == null })
                        } else {
                            result
                        }
                    }
                }.collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    when (result) {
                        is Result.Error -> {
                            _effect.emit(ShowError(result.error.asString()))
                        }

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