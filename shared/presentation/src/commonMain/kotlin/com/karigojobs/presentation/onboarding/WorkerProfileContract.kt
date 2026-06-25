package com.karigojobs.presentation.onboarding

data class WorkerProfileState(
    val currentStep : WorkerProfileStep = WorkerProfileStep.OWNER_NAME,
    val isLoading : Boolean = false,

    val ownerName : String = "",

    val businessName : String = "",

    val phoneNumber: String = "",
    val email: String = "",

    val gstNumber : String = "",
    val address : String = ""
)


sealed interface WorkerProfileEvent {
    data object OwnerNameComplete : WorkerProfileEvent

    data object BusinessNameCompete : WorkerProfileEvent
    data object BackToOwnerName : WorkerProfileEvent

    data object ContactDetailsComplete : WorkerProfileEvent
    data object BackToBusiness : WorkerProfileEvent

    data object ExtraInfoComplete : WorkerProfileEvent
    data object BackToContactDetails : WorkerProfileEvent


    data class OwnerNameField(val name : String) : WorkerProfileEvent
    data class BusinessNameField(val name : String) : WorkerProfileEvent
    data class PhoneNumberField(val phone : String) : WorkerProfileEvent
    data class EmailField(val email : String) : WorkerProfileEvent
    data class GstNumberField(val gstNumber : String) : WorkerProfileEvent
    data class AddressField(val address : String) : WorkerProfileEvent

}


sealed interface WorkerProfileEffect {
    data class ShowError(val message: String) : WorkerProfileEffect
    data object NavBack : WorkerProfileEffect
}

enum class WorkerProfileStep {
    OWNER_NAME,
    BUSINESS_NAME,
    CONTACT_DETAILS,
    EXTRA_INFO
}