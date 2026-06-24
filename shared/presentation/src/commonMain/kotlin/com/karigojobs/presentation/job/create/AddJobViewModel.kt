package com.karigojobs.presentation.job.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.material.GetAllMaterialsUseCase
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.client.InsertClientUseCase
import com.karigojobs.domain.usecase.client.IsClientExistUseCase
import com.karigojobs.domain.usecase.job.GetJobDetailsUseCase
import com.karigojobs.domain.usecase.job.GetJobLabourItemUseCase
import com.karigojobs.domain.usecase.job.GetJobMaterialItemsUseCase
import com.karigojobs.domain.usecase.job.SaveFullJobTransactionUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.job.create.AddJobEffect.ShowError
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
    private val getAllMaterialUseCase: GetAllMaterialsUseCase,
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val getJobLabourItemUseCase: GetJobLabourItemUseCase,
    private val getJobMaterialItemsUseCase: GetJobMaterialItemsUseCase,
    private val getClientUseCase: GetClientUseCase
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
        loadSelectedTradeType()
        loadMaterials()
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
                                status = job.status
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

    /** Fetches available materials from the local library. */
    private fun loadMaterials() {
        viewModelScope.launch {
            getAllMaterialUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(availableMaterials = result.data) }
                    }

                    is Result.Error -> {
                        _effect.emit(ShowError(result.error.asString()))
                    }
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
                    id = Uuid.random().toString(),
                    jobId = draftJobId,
                    itemName = event.labourItem.itemName,
                    quantity = event.labourItem.quantity.toLong(),
                    rate = event.labourItem.itemRate,
                    unit = event.labourItem.unit,
                    total = event.labourItem.quantity * event.labourItem.itemRate
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
                            total = newQty * item.rate
                        )
                    } else item
                }
                _state.update {
                    it.copy(labourItems = updateList)
                }
            }

            is AddJobIntent.MinusLabourItemQuantity -> {
                val updateList = _state.value.labourItems.map { item ->
                    if (item.id == event.itemId) {
                        if (item.quantity > 1) {
                            val newQty = item.quantity - 1
                            item.copy(
                                quantity = newQty,
                                total = newQty * item.rate
                            )
                        } else {
                            item.copy(
                                quantity = 1
                            )
                        }
                    } else item
                }
                _state.update {
                    it.copy(labourItems = updateList)
                }
            }

            is AddJobIntent.RemoveLabourItem -> {
                val filterList = _state.value.labourItems.filter {
                    it.id != event.id
                }
                _state.update {
                    it.copy(
                        labourItems = filterList
                    )
                }
            }

            is AddJobIntent.SelectTradeType -> {
                _state.update {
                    it.copy(
                        selectedTradeType = event.tradeType
                    )
                }
            }

            is AddJobIntent.ToggleMaterialLibrary -> {
                _state.update { it.copy(isMaterialLibraryModalOpen = event.isOpen) }
            }
            is AddJobIntent.IncreaseMaterialQuantity -> {
                val existingItem = _state.value.selectedMaterials.find { it.materialId == event.id }
                if (existingItem != null){
                    val updatedList  = _state.value.selectedMaterials.map { item ->
                        if (item.materialId == event.id){
                            val newQty = item.quantity + 1
                            item.copy(quantity = newQty, total = newQty * item.unitPrice)
                        }else item
                    }
                    _state.update { it.copy(selectedMaterials = updatedList) }
                }else{
                    val starterMat = _state.value.availableMaterials.find { it.id == event.id }
                    if (starterMat != null){
                        val newItem = JobMaterialItemModel(
                            id = Uuid.random().toString(),
                            jobId = draftJobId,
                            materialId = starterMat.id,
                            name =  starterMat.name,
                            unit = starterMat.unit,
                            unitPrice = starterMat.price,
                            quantity = 1,
                            total = starterMat.price
                        )
                        _state.update { it.copy(selectedMaterials = it.selectedMaterials + newItem) }
                    }
                }
            }
            is AddJobIntent.MinusMaterialQuantity -> {
                val updateList = _state.value.selectedMaterials.mapNotNull { item ->
                    if (item.materialId == event.id) {
                        if (item.quantity > 1) {
                            val newQty = item.quantity - 1
                            item.copy(quantity = newQty, total = newQty * item.unitPrice)
                        } else {
                            null // If quantity drops below 1, remove the item entirely
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
                            notes = ""
                        ),
                        jobLabourItemModel = _state.value.labourItems.map {
                            JobLabourItemModel(
                                id = it.id,
                                jobId = it.jobId,
                                itemName = it.itemName,
                                quantity = it.quantity,
                                rate = it.rate,
                                total = it.mainTotal,
                                unit = it.unit
                            )
                        },
                        jobMaterialItemModel = _state.value.selectedMaterials
                    )

                    when (result) {
                        is Result.Success -> {
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