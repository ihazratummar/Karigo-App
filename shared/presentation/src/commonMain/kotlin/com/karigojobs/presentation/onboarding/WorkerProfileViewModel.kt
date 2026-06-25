package com.karigojobs.presentation.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class WorkerProfileViewModel : ViewModel() {


    private val _state = MutableStateFlow(WorkerProfileState())
    val state : SharedFlow<WorkerProfileState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<WorkerProfileEffect>(replay = 0)
    val effect : SharedFlow<WorkerProfileEffect> = _effect.asSharedFlow()

    init {

    }


    fun onEvent(event: WorkerProfileEvent){
        when(event){
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

    private fun handleOwnerNameComplete(){
        _state.update { it.copy(currentStep = WorkerProfileStep.BUSINESS_NAME) }
    }

    private fun handleCompleteBusinessName() {
        _state.update { it.copy(currentStep = WorkerProfileStep.CONTACT_DETAILS) }
    }
    private fun handleBackToOwnerName() {
        _state.update { it.copy(currentStep = WorkerProfileStep.OWNER_NAME) }
    }

    private fun handleCompleteContact(){
        _state.update { it.copy(currentStep = WorkerProfileStep.EXTRA_INFO) }
    }
    private fun handleBackToBusinessName() {
        _state.update { it.copy(currentStep = WorkerProfileStep.BUSINESS_NAME) }
    }
    private fun handleExtraInfoComplete() {

    }
    private fun handleBackToContactDetails() {
        _state.update { it.copy(currentStep = WorkerProfileStep.CONTACT_DETAILS) }
    }


}