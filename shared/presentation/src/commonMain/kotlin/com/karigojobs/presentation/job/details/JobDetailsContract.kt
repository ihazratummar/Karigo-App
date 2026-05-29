package com.karigojobs.presentation.job.details

import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus


/**
 * @author hazratummar
 * Created on 26/05/26
 */
 


data class JobDetailsState(
    val jobModel: JobModel? = null,
    val clientModel: ClientModel? = null,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val isJobStatusModalOpen: Boolean = false,
    val jobLabourItems : List<JobLabourItemModel> = emptyList(),
    val jobMaterialItems: List<JobMaterialItemModel> = emptyList(),

    val isDeletePopUpOpen: Boolean = false
) {
    val labourTotal : Double get() = jobLabourItems.sumOf { it.quantity * it.rate }
    val materialTotal : Double get() = jobMaterialItems.sumOf { it.quantity * it.unitPrice }
}

sealed interface JobDetailsIntent {
    data class DeleteJob(val jobId: String) : JobDetailsIntent
    data class ChangeJobStatus(val id: String , val jobStatus: JobStatus) : JobDetailsIntent
    data class ToggleJobStatusModal (val isOpen : Boolean) : JobDetailsIntent

    data class DeletePopUpOpen (val isOpen : Boolean) : JobDetailsIntent
}


sealed interface JobDetailsEffect {
    data class ShowError(val message: String) : JobDetailsEffect
    data object NavigationBack : JobDetailsEffect
}