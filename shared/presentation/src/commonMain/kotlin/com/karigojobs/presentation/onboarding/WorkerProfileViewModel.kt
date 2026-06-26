package com.karigojobs.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.domain.usecase.settings.SaveWorkerProfileUseCase
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.presentation.erroMap.asString
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel managing the state and events for creating/updating the worker profile.
 */
class WorkerProfileViewModel(
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase,
    private val saveWorkerProfileUseCase: SaveWorkerProfileUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(WorkerProfileState())
    val state: StateFlow<WorkerProfileState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<WorkerProfileEffect>(replay = 0)
    val effect: SharedFlow<WorkerProfileEffect> = _effect.asSharedFlow()

    init {
        loadExistingProfile()
    }

    private fun loadExistingProfile() {
        viewModelScope.launch {
            getWorkerProfileUseCase().collectLatest { result ->
                if (result is Result.Success) {
                    val profile = result.data
                    if (profile != null) {
                        _state.update {
                            it.copy(
                                ownerName = profile.ownerName,
                                businessName = profile.businessName,
                                phoneNumber = profile.phone,
                                email = profile.email,
                                gstNumber = profile.gstNumber,
                                address = profile.address
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: WorkerProfileEvent) {
        when (event) {
            is WorkerProfileEvent.OwnerNameField -> {
                _state.update { it.copy(ownerName = event.name) }
            }

            is WorkerProfileEvent.BusinessNameField -> {
                _state.update { it.copy(businessName = event.name) }
            }

            is WorkerProfileEvent.PhoneNumberField -> {
                _state.update { it.copy(phoneNumber = event.phone) }
            }

            is WorkerProfileEvent.EmailField -> {
                _state.update { it.copy(email = event.email) }
            }

            is WorkerProfileEvent.GstNumberField -> {
                _state.update { it.copy(gstNumber = event.gstNumber) }
            }

            is WorkerProfileEvent.AddressField -> {
                _state.update { it.copy(address = event.address) }
            }

            WorkerProfileEvent.OwnerNameComplete -> handleOwnerNameComplete()
            WorkerProfileEvent.BusinessNameCompete -> handleCompleteBusinessName()
            WorkerProfileEvent.BackToOwnerName -> handleBackToOwnerName()
            WorkerProfileEvent.ContactDetailsComplete -> handleCompleteContact()
            WorkerProfileEvent.BackToBusiness -> handleBackToBusinessName()
            WorkerProfileEvent.ExtraInfoComplete -> handleExtraInfoComplete()
            WorkerProfileEvent.BackToContactDetails -> handleBackToContactDetails()
        }
    }

    private fun handleOwnerNameComplete() {
        if (_state.value.ownerName.isNotBlank()) {
            _state.update { it.copy(currentStep = WorkerProfileStep.BUSINESS_NAME) }
        }
    }

    private fun handleCompleteBusinessName() {
        if (_state.value.businessName.isNotBlank()) {
            _state.update { it.copy(currentStep = WorkerProfileStep.CONTACT_DETAILS) }
        }
    }

    private fun handleBackToOwnerName() {
        _state.update { it.copy(currentStep = WorkerProfileStep.OWNER_NAME) }
    }

    private fun handleCompleteContact() {
        _state.update { it.copy(currentStep = WorkerProfileStep.EXTRA_INFO) }
    }

    private fun handleBackToBusinessName() {
        _state.update { it.copy(currentStep = WorkerProfileStep.BUSINESS_NAME) }
    }

    private fun handleExtraInfoComplete() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val profile = WorkerProfileModel(
                id = 1L,
                ownerName = _state.value.ownerName,
                businessName = _state.value.businessName,
                phone = _state.value.phoneNumber,
                email = _state.value.email,
                gstNumber = _state.value.gstNumber,
                address = _state.value.address,
                logoPath = ""
            )
            val result = saveWorkerProfileUseCase(profile)
            _state.update { it.copy(isLoading = false) }
            when (result) {
                is Result.Success -> {
                    _effect.emit(WorkerProfileEffect.NavBack)
                }
                is Result.Error -> {
                    _effect.emit(WorkerProfileEffect.ShowError(result.error.asString()))
                }
            }
        }
    }

    private fun handleBackToContactDetails() {
        _state.update { it.copy(currentStep = WorkerProfileStep.CONTACT_DETAILS) }
    }
}