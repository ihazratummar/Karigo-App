package com.karigojobs.presentation.materials.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.materialCategory.DeleteMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.GetMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.InsertMaterialCategoryUseCase
import com.karigojobs.domain.usecase.materialCategory.UpdateMaterialCategoryUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType
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
import kotlin.uuid.Uuid

class MaterialCategoryViewModel(
    private val getMaterialCategoryUseCase: GetMaterialCategoryUseCase,
    private val insertMaterialCategoryUseCase: InsertMaterialCategoryUseCase,
    private val updateMaterialCategoryUseCase: UpdateMaterialCategoryUseCase,
    private val deleteMaterialCategoryUseCase: DeleteMaterialCategoryUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    private val _state = MutableStateFlow(MaterialCategoryState())
    val state: StateFlow<MaterialCategoryState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MaterialCategoryEffect>(replay = 0)
    val effect: SharedFlow<MaterialCategoryEffect> = _effect.asSharedFlow()

    fun onEvent(event: MaterialCategoryEvent) {
        when (event) {
            is MaterialCategoryEvent.LoadCategories -> {
                _state.update { it.copy(selectedTradeType = event.tradeType) }
                loadMaterialCategory(event.tradeType)
            }

            is MaterialCategoryEvent.ToggleManageCategoriesModal -> {
                _state.update { it.copy(isManageCategoriesModalOpen = event.isOpen, manageCategoryNewName = "") }
            }

            is MaterialCategoryEvent.ManageCategoryInputChanged -> {
                _state.update { it.copy(manageCategoryNewName = event.name) }
            }

            is MaterialCategoryEvent.CreateNewCategory -> {
                viewModelScope.launch {
                    val name = _state.value.manageCategoryNewName.trim()
                    val tradeType = _state.value.selectedTradeType
                    if (name.isNotBlank() && tradeType != null) {
                        if (_state.value.materialCategory.none { it.name.equals(name, ignoreCase = true) }) {
                            val newCategory = MaterialCategoryModel(
                                id = Uuid.random().toString(),
                                name = name,
                                tradeType = tradeType
                            )
                            val result = insertMaterialCategoryUseCase(newCategory)
                            if (result is Result.Success) {
                                _state.update { it.copy(manageCategoryNewName = "") }
                            } else if (result is Result.Error) {
                                _effect.emit(MaterialCategoryEffect.ShowError(result.error.asString()))
                            }
                        } else {
                            _effect.emit(MaterialCategoryEffect.ShowError("Category already exists"))
                        }
                    }
                }
            }

            is MaterialCategoryEvent.ToggleRenameCategoryDialog -> {
                _state.update {
                    it.copy(
                        renameCategoryTarget = event.category,
                        manageCategoryNewName = event.category?.name ?: ""
                    )
                }
            }

            is MaterialCategoryEvent.RenameCategoryInputChanged -> {
                _state.update { it.copy(manageCategoryNewName = event.name) }
            }

            is MaterialCategoryEvent.ConfirmRenameCategory -> {
                viewModelScope.launch {
                    val target = _state.value.renameCategoryTarget
                    val newName = _state.value.manageCategoryNewName.trim()

                    if (target != null && newName.isNotBlank() && newName != target.name) {
                        val updatedCategory = target.copy(name = newName)
                        val result = updateMaterialCategoryUseCase(updatedCategory)

                        if (result is Result.Success) {
                            _state.update { it.copy(renameCategoryTarget = null) }
                        } else if (result is Result.Error) {
                            _effect.emit(MaterialCategoryEffect.ShowError(result.error.asString()))
                        }
                    } else if (newName == target?.name) {
                        _state.update { it.copy(renameCategoryTarget = null) }
                    }
                }
            }

            is MaterialCategoryEvent.DeleteCategory -> {
                viewModelScope.launch {
                    val result = deleteMaterialCategoryUseCase(event.categoryId)
                    if (result is Result.Error) {
                        _effect.emit(MaterialCategoryEffect.ShowError(result.error.asString()))
                    }
                }
            }
        }
    }

    private fun loadMaterialCategory(tradeType: TradeType) {
        viewModelScope.launch {
            getMaterialCategoryUseCase(tradeType).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _effect.emit(MaterialCategoryEffect.ShowError(result.error.asString()))
                    }

                    is Result.Success -> {
                        _state.update { it.copy(materialCategory = result.data) }
                    }
                }
            }
        }
    }
}
