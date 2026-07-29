package com.karigojobs.presentation.estimate.add

import com.karigojobs.domain.repository.DeviceContact
import com.karigojobs.presentation.materials.list.MaterialListFilter
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.MaterialCategoryModel
import com.karigojobs.share.model.MaterialsModel
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.TradeType
import kotlin.time.Clock


/**
 * @author hazratummar
 * Created on 09/06/26
 */
 


data class SiteEstimateState(
    val estimateId: String? = null,
    val isLoading: Boolean = false,
    val isContactPickerOpen : Boolean = false,
    val contacts: List<DeviceContact> = emptyList(),
    val selectedClient: ClientModel? = null,
    val availableMaterials : List<MaterialsModel> = emptyList(),
    val selectedMaterials : List<SiteEstimateMaterial> = emptyList(),
    val projectTitle: String = "",
    val selectedDate: Long = Clock.System.now().toEpochMilliseconds(),
    val siteNotes : String = "",
    val isRateVisible : Boolean = true,
    val estimateMaterial: SiteEstimateMaterial? = null,
    val isDatePickerOpen : Boolean = false,
    val materialQuery : String = "",
    val materialFilter: MaterialListFilter = MaterialListFilter.All,
    val tradeTypes : Set<TradeType> ? = null,
    val selectedTradeType : TradeType? = null,
    val materialCategories: List<MaterialCategoryModel> = emptyList(),
    val selectedCategory: MaterialCategoryModel? = null,
    val isMaterialPickerOpen : Boolean = false
){
    val materialsTotal : Double get() = selectedMaterials.sumOf { it.quantity * it.rate }
    val canSave : Boolean get() = projectTitle.isNotBlank() && selectedClient != null && selectedMaterials.isNotEmpty()
}



sealed interface SiteEstimateEvent {

    data class ProjectTitleChange(val text: String) : SiteEstimateEvent
    data class SiteNoteChange(val text: String) : SiteEstimateEvent
    data class  ToggleContactPicker (val isOpen : Boolean) : SiteEstimateEvent
    data class ToggleDatePicker(val isOpen: Boolean) : SiteEstimateEvent
    data class SelectClient(val contact: DeviceContact) : SiteEstimateEvent
    data class SelectDate(val selectedDate : Long) : SiteEstimateEvent

    data class ToggleRateVisibility( val isVisible: Boolean) : SiteEstimateEvent
    data class SearchMaterials (val query: String) : SiteEstimateEvent
    data class SelectTradeType(val tradeType: TradeType?) : SiteEstimateEvent
    data class SelectCategory(val category: MaterialCategoryModel?) : SiteEstimateEvent

    data class ToggleMaterialPicker(val isOpen: Boolean) : SiteEstimateEvent
    data class AddMaterials(val materials: List<String?>) : SiteEstimateEvent

    data object SaveEstimate : SiteEstimateEvent

    data class IncreaseMaterialQuantity(val id: String) : SiteEstimateEvent
    data class DecreaseMaterialQuantity(val id: String) : SiteEstimateEvent
    data class ChangeMaterialQuantity(val id: String, val quantity : String ) : SiteEstimateEvent
    data class ChangeMaterialRate(val id: String, val rate : String ) : SiteEstimateEvent

    data class DeleteMaterial(val id: String) : SiteEstimateEvent
}

sealed interface EstimateEffect {
    data class ShowError(val message : String) : EstimateEffect
    data object NavigationBack : EstimateEffect
}
