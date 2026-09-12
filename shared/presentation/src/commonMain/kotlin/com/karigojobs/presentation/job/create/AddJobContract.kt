package com.karigojobs.presentation.job.create

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.TradeType


/**
 * @author hazratummar
 * Created on 23/05/26
 */


data class AddJobState(
    val jobId: String? = null,
    val isLoading: Boolean = false,

    // Job Details
    val clients: List<ClientModel> = emptyList(),
    val contacts: List<DeviceContact> = emptyList(),
    val selectedClient: ClientModel? = null,
    val tradeTypes: List<TradeType> = emptyList(),
    val selectedTradeType: TradeType? = null,
    val title: String = "",
    val status: JobStatus = JobStatus.PENDING,
    val isClientPickerModalOpen : Boolean = false,

    // Labour Mode: Per Item vs Hours
    val labourItems: List<JobLabourItemModel> = emptyList(),
    val isLabourCreateModalOpen: Boolean = false,
    val labourItemDraft: LabourItem = LabourItem(),
    val labourLogs: List<com.karigojobs.share.model.JobLabourLogModel> = emptyList(),

    // Materials
    val availableMaterials: List<MaterialsModel> = emptyList(),
    val selectedMaterials: List<JobMaterialItemModel> = emptyList(),
    val isMaterialPickerOpen : Boolean = false,
    val isCreateMaterialModalOpen: Boolean = false,
    val materialQuery: String = "",
    val selectedMaterialTradeType: TradeType? = null,
    val selectedMaterialCategory: MaterialCategoryModel? = null,
    val materialCategories: List<MaterialCategoryModel> = emptyList(),

    val includeLabourInInvoice: Boolean = true,
    val selectedLabourItemId: String? = null,
    val isLabourLogsModalOpen: Boolean = false,
    val selectedLabourLogs: List<com.karigojobs.share.model.JobLabourLogModel> = emptyList()
) {

    val canContinue : Boolean get() = (selectedClient != null && title != "") && (selectedMaterials.isNotEmpty() || labourItems.isNotEmpty())
    val labourTotal : Double get() = labourItems.sumOf { it.mainTotal }
    val materialTotal : Double get() = selectedMaterials.sumOf { it.quantity * it.unitPrice }

    val grandTotal : Double get() = if (includeLabourInInvoice) labourTotal + materialTotal else materialTotal

}

sealed interface AddJobIntent {
    data object LoadInitialData : AddJobIntent
    data class SelectClient(val contact: DeviceContact) : AddJobIntent
    data class SelectTradeType(val tradeType: TradeType) : AddJobIntent
    data class UpdateTitle(val title: String) : AddJobIntent
    data class ToggleClientPicker(val isOpen: Boolean) : AddJobIntent
    data class ToggleIncludeLabourInInvoice(val include: Boolean) : AddJobIntent

    // Labour Intents
    data class AddLabourItem(val labourItem: LabourItem) : AddJobIntent
    data class IncreaseLabourItemQuantity(val itemId: String) : AddJobIntent
    data class MinusLabourItemQuantity(val itemId: String) : AddJobIntent
    data class IncreaseLabourItemWorkersCount(val itemId: String) : AddJobIntent
    data class MinusLabourItemWorkersCount(val itemId: String) : AddJobIntent
    data class RemoveLabourItem(val id: String) : AddJobIntent
    data class LabourItemModalOpen(val isOpen: Boolean) : AddJobIntent
    data class ViewLabourLogs(val itemId: String) : AddJobIntent
    data object CloseLabourLogsModal : AddJobIntent

    data class ToggleMaterialPicker(val isOpen: Boolean) : AddJobIntent
    data class ToggleCreateMaterialModal(val isOpen: Boolean) : AddJobIntent
    data class CreateAndAddMaterial(
        val name: String,
        val tradeType: TradeType,
        val categoryName: String?,
        val price: Double,
        val unit: String
    ) : AddJobIntent
    data class SearchMaterials(val query: String) : AddJobIntent
    data class SelectMaterialTradeType(val tradeType: TradeType?) : AddJobIntent
    data class SelectMaterialCategory(val category: MaterialCategoryModel?) : AddJobIntent
    data class AddMaterials(val materials: List<String?>) : AddJobIntent
    data class ChangeMaterialQuantity(val id: String, val quantity: String) : AddJobIntent
    data class ChangeMaterialRate(val id: String, val rate: String) : AddJobIntent
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
    val workersCount: Int = 1,
    val unit: String = "Unit"
)