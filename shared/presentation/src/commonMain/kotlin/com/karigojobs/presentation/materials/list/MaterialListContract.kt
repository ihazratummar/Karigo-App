package com.karigojobs.presentation.materials.list

import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 04/06/26
 */
 



data class MaterialListState(
    val isLoading: Boolean = false,
    val selectTrades : Set<TradeType> = emptySet(),
    val selectedTradeType: TradeType? = null,
    val materialsList : List<MaterialsModel> = emptyList(),
    val isDeleting : Boolean = false,
    val materialQuery : String = "",
    val materialFilter: MaterialListFilter = MaterialListFilter.All,
    val workingMaterialId: String?= null,

    /// Edit Materials
    val isEditMaterialModalOpen : Boolean = false,
    val editingMaterial: MaterialsModel? = null,

    val isNewMaterialAddingModalOpen : Boolean = false,
    val newMaterialName: String = "",
    val newMaterialPrice: String = "",
    val newMaterialUnit: String = "",
    val newMaterialTradeType: TradeType? = null,
    val newMaterialCategoryName: String = "",
    val isAdding: Boolean = false,

    val materialCategory : List<MaterialCategoryModel> = emptyList(),
    val selectedCategory : MaterialCategoryModel? = null
) {
    val canAddNewMaterial : Boolean get() = newMaterialName.isNotBlank() && newMaterialPrice.isNotBlank()
            && newMaterialUnit.isNotBlank()
}


sealed interface MaterialListFilter {
    data object All : MaterialListFilter
    data class SelectedTrade(val selectedTrade: Set<TradeType>) : MaterialListFilter
}

sealed interface MaterialListEvent {
    data class ToggleDeleteMaterialClick(val isDeleting: Boolean, val materialId: String ? = null) : MaterialListEvent
    data class ToggleEditMaterialModal(val materialId: String? , val isEditing: Boolean) : MaterialListEvent
    data class EditMaterial(val materialId: String) : MaterialListEvent
    data class SearchMaterial(val query: String) : MaterialListEvent

    data class SelectTradeType(val tradeType: TradeType?) : MaterialListEvent
    data class SelectCategory(val category: MaterialCategoryModel?) : MaterialListEvent
    data class DeleteMaterial(val materialId: String) : MaterialListEvent
    data class EditMaterialName(val name: String) : MaterialListEvent

    data class EditMaterialPrice(val price: String) : MaterialListEvent
    data class EditMaterialUnit(val unit: String) : MaterialListEvent
    data class EditMaterialCategoryName(val name: String) : MaterialListEvent
    data class EditMaterialTradeType(val tradeType: TradeType) : MaterialListEvent
    data object UpdateMaterials : MaterialListEvent

    // New Material Adding
    data class NewMaterialTradeType(val tradeType: TradeType?) : MaterialListEvent
    data class ToggleAddMaterialModal(val isOpen: Boolean) : MaterialListEvent
    data object AddMaterial : MaterialListEvent
    data class NewMaterialName(val name: String) : MaterialListEvent
    data class NewMaterialUnit(val unit: String) : MaterialListEvent
    data class NewMaterialRate(val rate: String) : MaterialListEvent
    data class NewMaterialCategoryName(val name: String) : MaterialListEvent
}



sealed interface MaterialScreenEffect {
    data class ShowError(val message : String) : MaterialScreenEffect
}
