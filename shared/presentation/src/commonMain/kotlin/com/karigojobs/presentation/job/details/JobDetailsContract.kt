package com.karigojobs.presentation.job.details

import com.karigojobs.share.model.ClientModel
import com.karigojobs.share.model.JobLabourItemModel
import com.karigojobs.share.model.JobMaterialItemModel
import com.karigojobs.share.model.JobModel
import com.karigojobs.share.model.JobStatus
import com.karigojobs.share.model.WorkerProfileModel
import com.karigojobs.share.model.JobPaymentModel


/**
 * @author hazratummar
 * Created on 26/05/26
 */
 


data class JobDetailsState(
    val jobModel: JobModel? = null,
    val clientModel: ClientModel? = null,
    val workerProfileModel: WorkerProfileModel? = null,
    val isLoading: Boolean = false,
    val isDeleting: Boolean = false,
    val isJobStatusModalOpen: Boolean = false,
    val jobLabourItems : List<JobLabourItemModel> = emptyList(),
    val jobMaterialItems: List<JobMaterialItemModel> = emptyList(),
    val isDeletePopUpOpen: Boolean = false,

    val isPro: Boolean = false,
    val jobPayments: List<JobPaymentModel> = emptyList(),
    val showProDialog: Boolean = false,
    val proDialogFeatureName: String = ""
) {
    val labourTotal : Double get() = jobLabourItems.sumOf { it.quantity * it.rate }
    val materialTotal : Double get() = jobMaterialItems.sumOf { it.quantity * it.unitPrice }

    val paymentsTotal : Double get() = jobPayments.sumOf { it.amount }
    val remainingBalance : Double get() = (jobModel?.total ?: 0.0) - paymentsTotal
    val paidPercentage : Float get() = if (jobModel != null && jobModel.total > 0.0) (paymentsTotal / jobModel.total).toFloat() else 0f
}

sealed interface JobDetailsIntent {
    data class DeleteJob(val jobId: String) : JobDetailsIntent
    data class ChangeJobStatus(val id: String , val jobStatus: JobStatus) : JobDetailsIntent
    data class ToggleJobStatusModal (val isOpen : Boolean) : JobDetailsIntent

    data class DeletePopUpOpen (val isOpen : Boolean) : JobDetailsIntent
    data class GenerateInvoicePdf(val currencySymbol: String) : JobDetailsIntent
    data class ShareInvoiceOnWhatsapp(val currencySymbol: String) : JobDetailsIntent

    data class AddPayment(val amount: Double, val paymentMethod: String, val date: Long, val note: String) : JobDetailsIntent
    data class DeletePayment(val paymentId: String) : JobDetailsIntent
    data class ToggleProDialog(val isOpen: Boolean, val featureName: String = "") : JobDetailsIntent
}


sealed interface JobDetailsEffect {
    data class ShowError(val message: String) : JobDetailsEffect
    data object NavigationBack : JobDetailsEffect
    data class ShareInvoicePdf(val html: String, val jobTitle: String) : JobDetailsEffect
    data class ShareTextOnWhatsapp(val text: String) : JobDetailsEffect
    data object NavigateToPaywall : JobDetailsEffect
}