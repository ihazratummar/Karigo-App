package com.karigojobs.presentation.job.create

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 23/05/26
 */


data class AddJobState(
    val isLoading: Boolean = false,

    // Job Details
    val clients: List<ClientModel> = emptyList(),
    val contacts: List<DeviceContact> = emptyList(),
    val selectedClient: ClientModel? = null,
    val tradeTypes: List<TradeType> = emptyList(),
    val selectedTradeType: TradeType? = null,
    val title: String = "",
    val isClientPickerModalOpen : Boolean = false,

    // Labour Mode: Per Item vs Hours
    val labourItems: List<JobLabourItemModel> = emptyList(),
    val isLabourCreateModalOpen: Boolean = false,
    val labourItemDraft: LabourItem = LabourItem(),

    // Materials
    val availableMaterials: List<MaterialsModel> = emptyList(),
    val selectedMaterials: List<JobMaterialItemModel> = emptyList(),
    val isMaterialLibraryModalOpen : Boolean = false

    ){

    val canContinue : Boolean get() = (selectedClient != null && title != "") && (selectedMaterials.isNotEmpty() || labourItems.isNotEmpty())
    val labourTotal : Double get() = labourItems.sumOf { it.quantity * it.rate }
    val materialTotal : Double get() = selectedMaterials.sumOf { it.quantity * it.unitPrice }

    val grandTotal : Double get() = labourTotal + materialTotal

}

sealed interface AddJobIntent {
    data object LoadInitialData : AddJobIntent
    data class SelectClient(val contact: DeviceContact) : AddJobIntent
    data class SelectTradeType(val tradeType: TradeType) : AddJobIntent
    data class UpdateTitle(val title: String) : AddJobIntent
    data class ToggleClientPicker(val isOpen: Boolean) : AddJobIntent

    // Labour Intents
    data class AddLabourItem(val labourItem: LabourItem) : AddJobIntent
    data class IncreaseLabourItemQuantity(val itemId: String) : AddJobIntent
    data class MinusLabourItemQuantity(val itemId: String) : AddJobIntent
    data class RemoveLabourItem(val id: String) : AddJobIntent
    data class LabourItemModalOpen(val isOpen: Boolean) : AddJobIntent

    data class ToggleMaterialLibrary(val isOpen: Boolean) : AddJobIntent
    data class IncreaseMaterialQuantity(val id: String) : AddJobIntent
    data class MinusMaterialQuantity(val id: String) : AddJobIntent
    data class RemoveMaterial(val id: String) : AddJobIntent

    data object SaveJob: AddJobIntent

}


sealed interface AddJobEffect {
    data object NavigateBack : AddJobEffect
    data class ShowError (val message: String) : AddJobEffect
}

data class LabourItem(
    val itemName: String = "",
    val itemRate: Double = 0.0,
    val quantity: Int = 1,
    val unit: String = "Unit"
)