package com.karigojobs.presentation.estimate.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.client.InsertClientUseCase
import com.karigojobs.domain.usecase.client.IsClientExistUseCase
import com.karigojobs.domain.usecase.estimate.AddNewSiteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateByIdUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateMaterialsUseCase
import com.karigojobs.domain.usecase.estimate.UpdateSiteEstimateUseCase
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import com.karigojobs.domain.usecase.materialCategory.GetMaterialCategoryUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.estimate.add.EstimateEffect.*
import com.karigojobs.presentation.materials.list.MaterialListFilter.All
import com.karigojobs.presentation.materials.list.MaterialListFilter.SelectedTrade
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.SiteEstimateMaterial
import com.karigojobs.share.model.SiteEstimateModel
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
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
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * @author hazratummar
 * Created on 09/06/26
 */

class AddEstimateViewModel(
    private val estimateId: String? = null,
    private val deviceContactProvider: DeviceContactProvider,
    private val isClientExistUseCase: IsClientExistUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val searchMaterialsUseCase: SearchMaterialsUseCase,
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val insertClientUseCase: InsertClientUseCase,
    private val addNewSiteEstimateUseCase: AddNewSiteEstimateUseCase,
    private val getEstimateByIdUseCase: GetEstimateByIdUseCase,
    private val getEstimateMaterialsUseCase: GetEstimateMaterialsUseCase,
    private val updateSiteEstimateUseCase: UpdateSiteEstimateUseCase,
    private val getMaterialCategoryUseCase: GetMaterialCategoryUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    @OptIn(ExperimentalUuidApi::class)
    val draftEstimateId = estimateId ?: Uuid.random().toString()

    /**
     * State Declaration
     */
    private val _state = MutableStateFlow(SiteEstimateState(estimateId = estimateId))
    val state: StateFlow<SiteEstimateState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EstimateEffect>(replay = 0)
    val effect: SharedFlow<EstimateEffect> = _effect.asSharedFlow()


    init {
        analytics.logScreenView(AnalyticsEvent.Screen.ADD_ESTIMATE)
        loadMaterials()
        loadSelectedTrades()
        observeCategories()
        if (estimateId != null) {
            loadExistingEstimate()
        }
    }

    private fun loadExistingEstimate() {
        if (estimateId == null) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            // Load Estimate Details
            getEstimateByIdUseCase(estimateId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                projectTitle = result.data.projectTitle,
                                siteNotes = result.data.siteNote ?: "",
                                selectedDate = result.data.date,
                                isRateVisible = result.data.showRate,
                            )
                        }
                        
                        // Fetch full client details
                        val clientResult = getClientUseCase(result.data.clientId)
                        if (clientResult is Result.Success) {
                            _state.update { it.copy(selectedClient = clientResult.data) }
                        } else {
                            // Fallback to minimal client info if full fetch fails
                             _state.update {
                                it.copy(
                                    selectedClient = ClientModel(
                                        id = result.data.clientId,
                                        name = result.data.clientName,
                                        phone = "", 
                                        email = "",
                                        address = ""
                                    )
                                )
                            }
                        }
                    }
                    is Result.Error -> {
                        _effect.emit(ShowError(result.error.asString()))
                    }
                }
            }
        }

        viewModelScope.launch {
            // Load Estimate Materials
            getEstimateMaterialsUseCase(estimateId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { 
                            it.copy(
                                selectedMaterials = result.data,
                                isLoading = false 
                            ) 
                        }
                    }
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(ShowError(result.error.asString()))
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
                                tradeTypes = result.data
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
                        is All -> null
                        is SelectedTrade -> filter.selectedTrade
                    }
                    val queryCategoryId = if (category?.id == "uncategorized") null else category?.id
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
                                    availableMaterials = result.data
                                )
                            }
                        }
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCategories() {
        viewModelScope.launch {
            _state.map { it.selectedTradeType }
                .distinctUntilChanged()
                .flatMapLatest { tradeType ->
                    getMaterialCategoryUseCase(tradeType)
                }
                .collectLatest { result ->
                    if (result is Result.Success) {
                        _state.update { it.copy(materialCategories = result.data) }
                    }
                }
        }
    }


    private fun loadContacts() {
        if (_state.value.contacts.isNotEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val contacts = deviceContactProvider.getDeviceContacts()
            _state.update { it.copy(contacts = contacts, isLoading = false) }
        }
    }

    @OptIn(ExperimentalUuidApi::class)
    fun onEvent(event: SiteEstimateEvent) {
        when (event) {
            is SiteEstimateEvent.ToggleContactPicker -> {
                _state.update { it.copy(isContactPickerOpen = event.isOpen) }
                loadContacts()
            }

            is SiteEstimateEvent.SelectClient -> {
                viewModelScope.launch {
                    val safePhone = event.contact.phoneNumber.firstOrNull() ?: ""
                    when (val result = isClientExistUseCase.invoke(safePhone)) {
                        is Result.Success -> {
                            if (result.data == null) {
                                val client = ClientModel(
                                    id = Uuid.random().toString(),
                                    name = event.contact.name,
                                    phone = safePhone,
                                    email = "",
                                    address = "",
                                )
                                insertClientUseCase.invoke(clientModel = client)
                                _state.update {
                                    it.copy(
                                        selectedClient = client
                                    )
                                }
                            } else {
                                _state.update {
                                    it.copy(
                                        selectedClient = result.data
                                    )
                                }
                            }
                            _state.update { it.copy(isContactPickerOpen = false) }
                        }

                        is Result.Error -> {
                            _effect.emit(ShowError(result.error.asString()))
                        }
                    }
                }
            }

            is SiteEstimateEvent.SelectDate -> {
                _state.update { it.copy(selectedDate = event.selectedDate) }
            }
            is SiteEstimateEvent.ToggleDatePicker -> {
                _state.update { it.copy(isDatePickerOpen = event.isOpen) }
            }

            is SiteEstimateEvent.ProjectTitleChange ->{
                _state.update { it.copy(projectTitle = event.text) }
            }
            is SiteEstimateEvent.SiteNoteChange -> {
                _state.update { it.copy(siteNotes = event.text) }
            }

            is SiteEstimateEvent.ToggleRateVisibility -> {
                _state.update { it.copy(isRateVisible = event.isVisible) }
            }

            is SiteEstimateEvent.SearchMaterials -> {
                _state.update { it.copy(materialQuery = event.query) }
            }

            is SiteEstimateEvent.SelectTradeType -> {
                _state.update {
                    it.copy(
                        selectedTradeType = event.tradeType,
                        materialFilter = if (event.tradeType == null) All
                        else SelectedTrade(setOf(event.tradeType)),
                        selectedCategory = null
                    )
                }
            }

            is SiteEstimateEvent.SelectCategory -> {
                _state.update { it.copy(selectedCategory = event.category) }
            }

            is SiteEstimateEvent.ToggleMaterialPicker -> {
                _state.update {
                    it.copy(
                        isMaterialPickerOpen = event.isOpen,
                        materialQuery = if (!event.isOpen) "" else it.materialQuery,
                        selectedTradeType = if (!event.isOpen) null else it.selectedTradeType,
                        selectedCategory = if (!event.isOpen) null else it.selectedCategory,
                        materialFilter = if (!event.isOpen) All else it.materialFilter
                    )
                }
            }

            is SiteEstimateEvent.AddMaterials -> {
                _state.update { currentState ->
                    val existingMaterialIds = currentState.selectedMaterials.map { it.materialId }.toSet()

                    // Keep existing items that are still in the selection
                    val updatedList = currentState.selectedMaterials.filter { it.materialId in event.materials }.toMutableList()

                    // Add new items from availableMaterials
                    val newMaterialIds = event.materials.filter { it !in existingMaterialIds }

                    newMaterialIds.forEach { id ->
                        currentState.availableMaterials.find { it.id == id }?.let { material ->
                            updatedList.add(
                                SiteEstimateMaterial(
                                    id = Uuid.random().toString(),
                                    estimateId = draftEstimateId,
                                    materialId = material.id,
                                    materialName = material.name,
                                    quantity = 1.0,
                                    unit = material.unit,
                                    rate = material.price
                                )
                            )
                        }
                    }

                    currentState.copy(selectedMaterials = updatedList)
                }
            }

            SiteEstimateEvent.SaveEstimate -> {
                viewModelScope.launch {
                    val estimateModel = SiteEstimateModel(
                        id = draftEstimateId,
                        projectTitle = _state.value.projectTitle,
                        siteNote = _state.value.siteNotes,
                        clientId = _state.value.selectedClient?.id ?: "",
                        clientName = _state.value.selectedClient?.name ?: "",
                        date = _state.value.selectedDate,
                        showRate = _state.value.isRateVisible,
                        totalItems = _state.value.selectedMaterials.size.toDouble(),
                        total = _state.value.materialsTotal
                    )
                    
                    val result = if (estimateId == null) {
                        addNewSiteEstimateUseCase(
                            siteEstimateModel = estimateModel,
                            siteEstimateMaterials = _state.value.selectedMaterials
                        )
                    } else {
                        updateSiteEstimateUseCase(
                            siteEstimateModel = estimateModel,
                            siteEstimateMaterials = _state.value.selectedMaterials
                        )
                    }

                    when (result) {
                        is Result.Error -> {
                            _effect.emit(ShowError(result.error.toString()))
                        }
                        is Result.Success -> {
                            analytics.logEvent(AnalyticsEvent.Event.ESTIMATE_CREATED)
                            _effect.emit(NavigationBack)
                        }
                    }
                }
            }

            is SiteEstimateEvent.ChangeMaterialQuantity -> {
                val updatedList = _state.value.selectedMaterials.map { item ->
                    if (item.id == event.id) {
                        val raw = event.quantity
                        val parsed = raw.toDoubleOrNull()
                        item.copy(
                            quantityInput = raw,                          // always update display
                            quantity = when {
                                raw.isEmpty() -> item.quantity            // keep old value while erasing
                                parsed == null -> item.quantity           // invalid input, keep old
                                parsed < 1.0 -> 1.0                      // enforce minimum
                                else -> parsed
                            }
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updatedList) }
            }
            is SiteEstimateEvent.ChangeMaterialRate -> {
                val updatedList = _state.value.selectedMaterials.map { item ->
                    if (item.id == event.id) {
                        val raw = event.rate
                        val parsed = raw.toDoubleOrNull()
                        item.copy(
                            rateInput = raw,
                            rate = when {
                                raw.isEmpty() -> item.rate
                                parsed == null -> item.rate
                                parsed < 0.0 -> 0.0
                                else -> parsed
                            }
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updatedList) }
            }
            is SiteEstimateEvent.DecreaseMaterialQuantity -> {
                val updateList = _state.value.selectedMaterials.map { item ->
                    if (item.id == event.id) {
                        val newQty = if (item.quantity > 1) item.quantity - 1 else 1.0
                        item.copy(
                            quantity = newQty,
                            quantityInput = newQty.toString()  // ← sync
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updateList) }
            }
            is SiteEstimateEvent.DeleteMaterial -> {
                val filterList = _state.value.selectedMaterials.filter {
                    it.id != event.id
                }
                _state.update {
                    it.copy(selectedMaterials = filterList)
                }
            }
            is SiteEstimateEvent.IncreaseMaterialQuantity -> {
                val updateList = _state.value.selectedMaterials.map { item ->
                    if (item.id == event.id) {
                        val newQty = item.quantity + 1
                        item.copy(
                            quantity = newQty,
                            quantityInput = newQty.toString()  // ← sync
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updateList) }
            }
        }
    }

}