package com.karigojobs.presentation.materials.list

import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.TradeType

data class MaterialCategoryState(
    val isLoading: Boolean = false,
    val materialCategory: List<MaterialCategoryModel> = emptyList(),
    val selectedTradeType: TradeType? = null,
    
    // Manage Categories State
    val isManageCategoriesModalOpen: Boolean = false,
    val manageCategoryNewName: String = "",
    val renameCategoryTarget: MaterialCategoryModel? = null
)

sealed interface MaterialCategoryEvent {
    data class LoadCategories(val tradeType: TradeType) : MaterialCategoryEvent
    data class ToggleManageCategoriesModal(val isOpen: Boolean) : MaterialCategoryEvent
    data class ManageCategoryInputChanged(val name: String) : MaterialCategoryEvent
    data object CreateNewCategory : MaterialCategoryEvent
    data class ToggleRenameCategoryDialog(val category: MaterialCategoryModel?) : MaterialCategoryEvent
    data class RenameCategoryInputChanged(val name: String) : MaterialCategoryEvent
    data object ConfirmRenameCategory : MaterialCategoryEvent
    data class DeleteCategory(val categoryId: String) : MaterialCategoryEvent
}

sealed interface MaterialCategoryEffect {
    data class ShowError(val message: String) : MaterialCategoryEffect
}
