package com.karigojobs.presentation.job.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.domain.usecase.InsertClientUseCase
import com.karigojobs.domain.usecase.IsClientExistUseCase
import com.karigojobs.domain.usecase.SaveFullJobTransactionUseCase
import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class AddJobViewModel(
    private val deviceContactProvider: DeviceContactProvider,
    private val saveFullJobTransactionUseCase: SaveFullJobTransactionUseCase,
    private val isClientExistUseCase: IsClientExistUseCase,
    private val insertClientUseCase: InsertClientUseCase
) : ViewModel() {

    @OptIn(ExperimentalUuidApi::class)
    private val draftJobId = Uuid.random().toString()

    private val _state = MutableStateFlow(AddJobState())
    val addJobState: StateFlow<AddJobState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<AddJobEffect>(replay = 0)
    val addJobEffect: SharedFlow<AddJobEffect> = _effect.asSharedFlow()


    init {
        // Removed loadContacts() from init to avoid crash before permission is granted
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
    fun event(event: AddJobIntent) {
        when (event) {
            AddJobIntent.LoadInitialData -> TODO()
            is AddJobIntent.SelectClient -> {
                viewModelScope.launch {
                    val safePhone = event.contact.phoneNumber.firstOrNull() ?: ""

                    val clientData = isClientExistUseCase.invoke(safePhone)
                    if (clientData == null) {
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
                                selectedClient = clientData
                            )
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
                        item.copy(
                            quantity = item.quantity + 1
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
                        if (item.quantity > 1){
                            item.copy(
                                quantity = item.quantity - 1
                            )
                        }else{
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


            is AddJobIntent.UpdateLabourDraft -> TODO()

            is AddJobIntent.AddMaterial -> TODO()
            is AddJobIntent.RemoveMaterial -> TODO()

            AddJobIntent.SaveJob -> {
                viewModelScope.launch {
                    saveFullJobTransactionUseCase.invoke(
                        job = JobModel(
                            id = draftJobId,
                            clientId = _state.value.selectedClient?.id ?: "",
                            clientName = _state.value.selectedClient?.name ?: "",
                            title = _state.value.title,
                            description = _state.value.title,
                            status = JobStatus.DRAFT,
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
                    _effect.emit(AddJobEffect.NavigateBack)
                }
            }
        }
    }

}