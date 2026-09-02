package com.karigojobs.presentation.job.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojob.share.utils.EpochUtils
import com.karigojob.share.utils.UuidGenerator
import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.material.SearchMaterialsUseCase
import com.karigojobs.domain.usecase.materialCategory.GetMaterialCategoryUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.client.InsertClientUseCase
import com.karigojobs.domain.usecase.client.IsClientExistUseCase
import com.karigojobs.domain.usecase.job.GetJobDetailsUseCase
import com.karigojobs.domain.usecase.job.GetJobLabourItemUseCase
import com.karigojobs.domain.usecase.job.GetJobMaterialItemsUseCase
import com.karigojobs.domain.usecase.job.SaveFullJobTransactionUseCase
import com.karigojobs.domain.usecase.trade.GetSelectedTradeTypeUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.job.create.AddJobEffect.ShowError
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.TradeType
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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * @author hazratummar
 * Created on 23/05/26
 */

/**
 * ViewModel managing the state and events for adding or editing a job.
 */
class AddJobViewModel(
    private val jobId: String? = null,
    private val clientId: String? = null,
    private val deviceContactProvider: DeviceContactProvider,
    private val saveFullJobTransactionUseCase: SaveFullJobTransactionUseCase,
    private val isClientExistUseCase: IsClientExistUseCase,
    private val insertClientUseCase: InsertClientUseCase,
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase,
    private val searchMaterialsUseCase: SearchMaterialsUseCase,
    private val getMaterialCategoryUseCase: GetMaterialCategoryUseCase,
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val getJobLabourItemUseCase: GetJobLabourItemUseCase,
    private val getJobMaterialItemsUseCase: GetJobMaterialItemsUseCase,
    private val getJobLabourLogsUseCase: com.karigojobs.domain.usecase.job.GetJobLabourLogsUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val incrementJobCountUseCase: com.karigojobs.domain.usecase.monetization.IncrementJobCountUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    /** Unique draft or existing job ID. */
    @OptIn(ExperimentalUuidApi::class)
    private val draftJobId = jobId ?: Uuid.random().toString()

    /** Internal UI state. */
    private val _state = MutableStateFlow(AddJobState(jobId = jobId))
    /** Exposed UI state for observers. */
    val addJobState: StateFlow<AddJobState> = _state.asStateFlow()

    /** Internal side effects. */
    private val _effect = MutableSharedFlow<AddJobEffect>(replay = 0)
    /** Exposed side effects (e.g., navigation, toasts). */
    val addJobEffect: SharedFlow<AddJobEffect> = _effect.asSharedFlow()


    /** Initializes data required for the job form. */
    init {
        analytics.logScreenView(AnalyticsEvent.Screen.ADD_JOB)
        loadSelectedTradeType()
        observeMaterialsSearch()
        observeCategoriesLoading()
        if (jobId != null) {
            loadExistingJob()
        }
        loadClient()
    }


    /** Loads pre-selected client details if [clientId] was provided. */
    private fun loadClient() {
        viewModelScope.launch {
            if (clientId != null) {
                val clientResult = getClientUseCase(clientId)
                when(clientResult){
                    is Result.Error -> {
                        _effect.emit(ShowError(clientResult.error.asString()))
                    }
                    is Result.Success -> {
                        _state.update { it.copy(selectedClient = clientResult.data) }
                    }
                }

            }
        }
    }

    /** Fetches and populates existing job details, labour, and materials. */
    private fun loadExistingJob() {
        if (jobId == null) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getJobDetailsUseCase(jobId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        val job = result.data ?: return@collectLatest
                        _state.update {
                            it.copy(
                                title = job.title,
                                selectedTradeType = job.tradeType,
                                status = job.status,
                                includeLabourInInvoice = job.includeLabourInInvoice
                            )
                        }

                        // Load full client details
                        val clientResult = getClientUseCase(job.clientId)
                        if (clientResult is Result.Success) {
                            _state.update { it.copy(selectedClient = clientResult.data) }
                        } else {
                            _state.update {
                                it.copy(
                                    selectedClient = ClientModel(
                                        id = job.clientId,
                                        name = job.clientName,
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
            getJobLabourItemUseCase(jobId).collectLatest { result ->
                if (result is Result.Success) {
                    _state.update { it.copy(labourItems = result.data) }
                }
            }
        }

        viewModelScope.launch {
            getJobMaterialItemsUseCase(jobId).collectLatest { result ->
                if (result is Result.Success) {
                    _state.update { it.copy(selectedMaterials = result.data, isLoading = false) }
                }
            }
        }
    }

    /** Fetches device contacts for client selection. */
    private fun loadContacts() {
        if (_state.value.contacts.isNotEmpty()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            val contacts = deviceContactProvider.getDeviceContacts()
            _state.update { it.copy(contacts = contacts, isLoading = false) }
        }
    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeMaterialsSearch() {
        viewModelScope.launch {
            combine(
                _state.map { it.materialQuery }.distinctUntilChanged(),
                _state.map { it.selectedMaterialTradeType }.distinctUntilChanged(),
                _state.map { it.selectedMaterialCategory }.distinctUntilChanged()
            ) { query, tradeType, category -> Triple(query, tradeType, category) }
                .debounce(300.milliseconds)
                .flatMapLatest { (query, tradeType, category) ->
                    _state.update { it.copy(isLoading = true) }
                    val tradeTypes = tradeType?.let { setOf(it) }
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
                }
                .collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    if (result is Result.Success) {
                        _state.update { it.copy(availableMaterials = result.data) }
                    } else if (result is Result.Error) {
                        _effect.emit(ShowError(result.error.asString()))
                    }
                }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun observeCategoriesLoading() {
        viewModelScope.launch {
            _state.map { it.selectedMaterialTradeType }
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

    /** Fetches available trade types and sets a default selection. */
    private fun loadSelectedTradeType() {
        _state.update { it.copy(isLoading = false) }
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                tradeTypes = result.data.toList(),
                                selectedTradeType = it.selectedTradeType
                                    ?: result.data.firstOrNull()
                            )
                        }

                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        _effect.emit(ShowError(message = result.error.toString()))
                    }
                }
            }
        }
    }


    fun onEvent(event: AddJobIntent) = event(event)

    /** Handles UI intents and updates state or triggers respective actions. */
    @OptIn(ExperimentalUuidApi::class)
    fun event(event: AddJobIntent) {
        when (event) {
            AddJobIntent.LoadInitialData -> {
                loadSelectedTradeType()
            }

            is AddJobIntent.SelectClient -> {
                viewModelScope.launch {
                    val safePhone = event.contact.phoneNumber.firstOrNull() ?: ""
                    val result = isClientExistUseCase.invoke(safePhone)
                    when (result) {
                        is Result.Success -> {
                            if (result.data == null) {
                                val client = ClientModel(
                                    id = clientId ?: Uuid.random().toString(),
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
                            _state.update { it.copy(isClientPickerModalOpen = false) }
                        }

                        is Result.Error -> {
                            _effect.emit(ShowError(result.error.asString()))
                        }
                    }
                }
            }

            is AddJobIntent.UpdateTitle -> {
                _state.update {
                    it.copy(
                        title = event.title
                    )
                }
            }

            is AddJobIntent.ToggleClientPicker -> {
                _state.update { it.copy(isClientPickerModalOpen = event.isOpen) }
                if (event.isOpen) {
                    loadContacts()
                }
            }

            is AddJobIntent.LabourItemModalOpen -> {
                _state.update { it.copy(isLabourCreateModalOpen = event.isOpen) }
            }

            is AddJobIntent.AddLabourItem -> {
                val newItem = JobLabourItemModel(
                    id = UuidGenerator.generate(),
                    jobId = _state.value.jobId ?: "",
                    itemName = event.labourItem.itemName,
                    rate = event.labourItem.itemRate,
                    quantity = event.labourItem.quantity.toLong(),
                    workersCount = event.labourItem.workersCount.toLong(),
                    total = event.labourItem.itemRate * event.labourItem.quantity * event.labourItem.workersCount,
                    unit = event.labourItem.unit
                )
                // 3. Update the State using pure Kotlin list addition (+)
                _state.value = _state.value.copy(
                    labourItems = _state.value.labourItems + newItem
                )

            }

            is AddJobIntent.IncreaseLabourItemQuantity -> {
                val updateList = _state.value.labourItems.map { item ->
                    if (item.id == event.itemId) {
                        val newQty = item.quantity + 1
                        item.copy(
                            quantity = newQty,
                            total = newQty * item.rate * item.workersCount
                        )
                    } else item
                }
                val newLog = com.karigojobs.share.model.JobLabourLogModel(
                    id = UuidGenerator.generate(),
                    labourItemId = event.itemId,
                    changeAmount = 1,
                    logType = "QUANTITY",
                    createdAt = EpochUtils.now()
                )
                _state.update {
                    it.copy(
                        labourItems = updateList,
                        labourLogs = it.labourLogs + newLog,
                        selectedLabourLogs = if (it.selectedLabourItemId == event.itemId) it.selectedLabourLogs + newLog else it.selectedLabourLogs
                    )
                }
            }

            is AddJobIntent.MinusLabourItemQuantity -> {
                var recordedLog: com.karigojobs.share.model.JobLabourLogModel? = null
                val updateList = _state.value.labourItems.map { item ->
                    if (item.id == event.itemId) {
                        if (item.quantity > 1) {
                            val newQty = item.quantity - 1
                            recordedLog = com.karigojobs.share.model.JobLabourLogModel(
                                id = UuidGenerator.generate(),
                                labourItemId = event.itemId,
                                changeAmount = -1,
                                logType = "QUANTITY",
                                createdAt = EpochUtils.now()
                            )
                            item.copy(
                                quantity = newQty,
                                total = newQty * item.rate * item.workersCount
                            )
                        } else {
                            item.copy(
                                quantity = 1
                            )
                        }
                    } else item
                }
                _state.update {
                    it.copy(
                        labourItems = updateList,
                        labourLogs = if (recordedLog != null) it.labourLogs + recordedLog!! else it.labourLogs,
                        selectedLabourLogs = if (recordedLog != null && it.selectedLabourItemId == event.itemId) it.selectedLabourLogs + recordedLog!! else it.selectedLabourLogs
                    )
                }
            }

            is AddJobIntent.IncreaseLabourItemWorkersCount -> {
                val updateList = _state.value.labourItems.map { item ->
                    if (item.id == event.itemId) {
                        val newQty = item.workersCount + 1
                        item.copy(
                            workersCount = newQty,
                            total = newQty * item.rate * item.quantity
                        )
                    } else item
                }
                val newLog = com.karigojobs.share.model.JobLabourLogModel(
                    id = UuidGenerator.generate(),
                    labourItemId = event.itemId,
                    changeAmount = 1,
                    logType = "WORKERS",
                    createdAt = EpochUtils.now()
                )
                _state.update {
                    it.copy(
                        labourItems = updateList,
                        labourLogs = it.labourLogs + newLog,
                        selectedLabourLogs = if (it.selectedLabourItemId == event.itemId) it.selectedLabourLogs + newLog else it.selectedLabourLogs
                    )
                }
            }

            is AddJobIntent.MinusLabourItemWorkersCount -> {
                var recordedLog: com.karigojobs.share.model.JobLabourLogModel? = null
                val updateList = _state.value.labourItems.map { item ->
                    if (item.id == event.itemId) {
                        if (item.workersCount > 1) {
                            val newQty = item.workersCount - 1
                            recordedLog = com.karigojobs.share.model.JobLabourLogModel(
                                id = UuidGenerator.generate(),
                                labourItemId = event.itemId,
                                changeAmount = -1,
                                logType = "WORKERS",
                                createdAt = EpochUtils.now()
                            )
                            item.copy(
                                workersCount = newQty,
                                total = newQty * item.rate * item.quantity
                            )
                        } else {
                            item.copy(
                                workersCount = 1
                            )
                        }
                    } else item
                }
                _state.update {
                    it.copy(
                        labourItems = updateList,
                        labourLogs = if (recordedLog != null) it.labourLogs + recordedLog!! else it.labourLogs,
                        selectedLabourLogs = if (recordedLog != null && it.selectedLabourItemId == event.itemId) it.selectedLabourLogs + recordedLog!! else it.selectedLabourLogs
                    )
                }
            }

            is AddJobIntent.RemoveLabourItem -> {
                val filterList = _state.value.labourItems.filter {
                    it.id != event.id
                }
                val filterLogs = _state.value.labourLogs.filter {
                    it.labourItemId != event.id
                }
                _state.update {
                    it.copy(
                        labourItems = filterList,
                        labourLogs = filterLogs
                    )
                }
            }

            is AddJobIntent.ToggleIncludeLabourInInvoice -> {
                _state.update { it.copy(includeLabourInInvoice = event.include) }
            }
            
            is AddJobIntent.ViewLabourLogs -> {
                _state.update { it.copy(selectedLabourItemId = event.itemId, isLabourLogsModalOpen = true) }
                viewModelScope.launch {
                    getJobLabourLogsUseCase(event.itemId).collectLatest { result ->
                        when(result) {
                            is Result.Success -> {
                                _state.update { state ->
                                    val dbLogs = result.data ?: emptyList()
                                    val sessionLogs = state.labourLogs.filter { log -> log.labourItemId == event.itemId }
                                    state.copy(selectedLabourLogs = (dbLogs + sessionLogs).distinctBy { it.id })
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
            
            AddJobIntent.CloseLabourLogsModal -> {
                _state.update { it.copy(isLabourLogsModalOpen = false, selectedLabourItemId = null, selectedLabourLogs = emptyList()) }
            }

            is AddJobIntent.SelectTradeType -> {
                _state.update {
                    it.copy(
                        selectedTradeType = event.tradeType
                    )
                }
            }

            is AddJobIntent.ToggleMaterialPicker -> {
                _state.update {
                    it.copy(
                        isMaterialPickerOpen = event.isOpen,
                        materialQuery = if (!event.isOpen) "" else it.materialQuery,
                        selectedMaterialTradeType = if (!event.isOpen) null else it.selectedMaterialTradeType,
                        selectedMaterialCategory = if (!event.isOpen) null else it.selectedMaterialCategory
                    )
                }
            }

            is AddJobIntent.SearchMaterials -> {
                _state.update { it.copy(materialQuery = event.query) }
            }

            is AddJobIntent.SelectMaterialTradeType -> {
                _state.update {
                    it.copy(
                        selectedMaterialTradeType = event.tradeType,
                        selectedMaterialCategory = null
                    )
                }
            }

            is AddJobIntent.SelectMaterialCategory -> {
                _state.update { it.copy(selectedMaterialCategory = event.category) }
            }

            is AddJobIntent.AddMaterials -> {
                _state.update { currentState ->
                    val existingMaterialIds = currentState.selectedMaterials.mapNotNull { it.materialId }.toSet()
                    val updatedList = currentState.selectedMaterials.filter { it.materialId in event.materials }.toMutableList()
                    val newMaterialIds = event.materials.filter { it !in existingMaterialIds }

                    newMaterialIds.forEach { id ->
                        currentState.availableMaterials.find { it.id == id }?.let { material ->
                            updatedList.add(
                                JobMaterialItemModel(
                                    id = Uuid.random().toString(),
                                    jobId = draftJobId,
                                    materialId = material.id,
                                    name = material.name,
                                    unit = material.unit,
                                    unitPrice = material.price,
                                    quantity = 1,
                                    total = material.price
                                )
                            )
                        }
                    }
                    currentState.copy(selectedMaterials = updatedList)
                }
            }

            is AddJobIntent.ChangeMaterialQuantity -> {
                val updatedList = _state.value.selectedMaterials.map { item ->
                    if (item.materialId == event.id) {
                        val cleanedInput = event.quantity.replace(",", ".")
                        val parsedQty = cleanedInput.toIntOrNull() ?: item.quantity
                        item.copy(
                            quantity = parsedQty,
                            quantityInput = event.quantity,
                            total = parsedQty * item.unitPrice
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updatedList) }
            }

            is AddJobIntent.ChangeMaterialRate -> {
                val updatedList = _state.value.selectedMaterials.map { item ->
                    if (item.materialId == event.id) {
                        val cleanedInput = event.rate.replace(",", ".")
                        val parsedRate = cleanedInput.toDoubleOrNull() ?: item.unitPrice
                        item.copy(
                            unitPrice = parsedRate,
                            unitPriceInput = event.rate,
                            total = item.quantity * parsedRate
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updatedList) }
            }

            is AddJobIntent.IncreaseMaterialQuantity -> {
                val updatedList = _state.value.selectedMaterials.map { item ->
                    if (item.materialId == event.id) {
                        val newQty = item.quantity + 1
                        item.copy(
                            quantity = newQty,
                            quantityInput = newQty.toString(),
                            total = newQty * item.unitPrice
                        )
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updatedList) }
            }

            is AddJobIntent.MinusMaterialQuantity -> {
                val updateList = _state.value.selectedMaterials.mapNotNull { item ->
                    if (item.materialId == event.id) {
                        if (item.quantity > 1) {
                            val newQty = item.quantity - 1
                            item.copy(
                                quantity = newQty,
                                quantityInput = newQty.toString(),
                                total = newQty * item.unitPrice
                            )
                        } else {
                            null
                        }
                    } else item
                }
                _state.update { it.copy(selectedMaterials = updateList) }
            }

            is AddJobIntent.RemoveMaterial -> {
                val filterList = _state.value.selectedMaterials.filter {
                    it.materialId != event.id
                }
                _state.update { it.copy(selectedMaterials = filterList) }
            }

            AddJobIntent.SaveJob -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    val result = saveFullJobTransactionUseCase.invoke(
                        job = JobModel(
                            id = draftJobId,
                            clientId = _state.value.selectedClient?.id ?: "",
                            clientName = _state.value.selectedClient?.name ?: "",
                            title = _state.value.title,
                            description = _state.value.title,
                            status = _state.value.status,
                            tradeType = _state.value.selectedTradeType ?: TradeType.PLUMBER,
                            materialTotal = _state.value.materialTotal,
                            total = _state.value.grandTotal,
                            notes = "",
                            includeLabourInInvoice = _state.value.includeLabourInInvoice
                        ),
                        jobLabourItemModel = _state.value.labourItems,
                        jobMaterialItemModel = _state.value.selectedMaterials,
                        jobLabourLogs = _state.value.labourLogs
                    )

                    when (result) {
                        is Result.Success -> {
                            if (jobId == null) {
                                incrementJobCountUseCase()
                            }
                            analytics.logEvent(AnalyticsEvent.Event.JOB_CREATED)
                            _effect.emit(AddJobEffect.NavigateBack)
                            _state.update { it.copy(isLoading = false) }
                        }

                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(ShowError(message = result.error.asString()))
                        }
                    }
                }
            }

        }
    }

}