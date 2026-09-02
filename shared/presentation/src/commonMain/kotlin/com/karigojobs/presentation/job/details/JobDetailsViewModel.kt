package com.karigojobs.presentation.job.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.job.ChangeJobStatusUseCase
import com.karigojobs.domain.usecase.job.DeleteJobUseCase
import com.karigojobs.domain.usecase.job.GetJobDetailsUseCase
import com.karigojobs.domain.usecase.job.GetJobLabourItemUseCase
import com.karigojobs.domain.usecase.job.GetJobMaterialItemsUseCase
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.job.details.JobDetailsEffect.*
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
import com.karigojobs.domain.usecase.monetization.ObserveProStatusUseCase
import com.karigojobs.domain.usecase.job.GetJobPaymentsUseCase
import com.karigojobs.domain.usecase.job.AddJobPaymentUseCase
import com.karigojobs.domain.usecase.job.DeleteJobPaymentUseCase
import com.karigojobs.share.model.JobPaymentModel
import com.karigojob.share.utils.UuidGenerator
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.karigojob.share.utils.EpochUtils


/**
 * @author hazratummar
 * Created on 26/05/26
 */

class JobDetailsViewModel(
    private val jobId: String,
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val changeJobStatusUseCase: ChangeJobStatusUseCase,
    private val deleteJobUseCase: DeleteJobUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val getJobLabourItemUseCase: GetJobLabourItemUseCase,
    private val getJobMaterialItemsUseCase: GetJobMaterialItemsUseCase,
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase,
    private val observeProStatusUseCase: ObserveProStatusUseCase,
    private val getJobPaymentsUseCase: GetJobPaymentsUseCase,
    private val addJobPaymentUseCase: AddJobPaymentUseCase,
    private val deleteJobPaymentUseCase: DeleteJobPaymentUseCase,
    private val analytics: AnalyticsLogger,
    private val observeMonthlyJobLimitUseCase: com.karigojobs.domain.usecase.monetization.ObserveMonthlyJobLimitUseCase? = null,
    private val incrementPdfCountUseCase: com.karigojobs.domain.usecase.monetization.IncrementPdfCountUseCase? = null
) : ViewModel() {

    private val _state = MutableStateFlow(JobDetailsState())
    val state: StateFlow<JobDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<JobDetailsEffect>(replay = 0)
    val effect: SharedFlow<JobDetailsEffect> = _effect.asSharedFlow()


    init {
        analytics.logScreenView(AnalyticsEvent.Screen.JOB_DETAILS)
        loadJob()
        loadJobLabourItem()
        loadJobMaterialItem()
        loadWorkerProfile()
        observeProStatus()
        observeQuota()
        loadPayments()
    }

    private fun observeQuota() {
        viewModelScope.launch {
            observeMonthlyJobLimitUseCase?.invoke()?.collectLatest { quota ->
                _state.update { it.copy(monthlyJobLimit = quota) }
            }
        }
    }


    fun onEvent(event: JobDetailsIntent) {
        when (event) {
            is JobDetailsIntent.ChangeJobStatus -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    val result =
                        changeJobStatusUseCase.invoke(jobId = event.id, jobStatus = event.jobStatus)
                    when (result) {
                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(ShowError(message = result.error.asString()))
                        }

                        is Result.Success -> {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    isJobStatusModalOpen = false
                                )
                            }
                        }
                    }
                }
            }

            is JobDetailsIntent.DeleteJob -> {
                _state.update { it.copy(isLoading = true) }
                viewModelScope.launch {
                    val result = deleteJobUseCase.invoke(jobId = event.jobId)
                    when (result) {
                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(ShowError(message = result.error.asString()))
                        }
                        is Result.Success -> {
                            _state.update { it.copy(isLoading = true, isDeletePopUpOpen = false) }
                            _effect.emit(NavigationBack)
                        }
                    }
                }
            }

            is JobDetailsIntent.ToggleJobStatusModal -> {
                _state.update { it.copy(isJobStatusModalOpen = event.isOpen) }
            }

            is JobDetailsIntent.DeletePopUpOpen -> {
                _state.update { it.copy(isDeletePopUpOpen = event.isOpen) }
            }

            is JobDetailsIntent.GenerateInvoicePdf -> {
                if (!_state.value.isPro && _state.value.monthlyJobLimit.isPdfQuotaExhausted) {
                    _state.update { it.copy(showProDialog = true, proDialogFeatureName = "PDF_EXPORT") }
                    return
                }
                val job = _state.value.jobModel ?: return
                val client = _state.value.clientModel
                val worker = _state.value.workerProfileModel
                val labour = _state.value.jobLabourItems
                val materials = _state.value.jobMaterialItems
                val payments = _state.value.jobPayments
                val html = InvoiceHtmlBuilder.buildInvoiceHtml(
                    job = job,
                    client = client,
                    workerProfile = worker,
                    labourItems = labour,
                    materialItems = materials,
                    payments = payments,
                    currencySymbol = event.currencySymbol
                )
                val jobTitle = "Invoice_${job.title.replace(" ", "_")}"
                viewModelScope.launch {
                    if (!_state.value.isPro) incrementPdfCountUseCase?.invoke()
                    _effect.emit(JobDetailsEffect.ShareInvoicePdf(html, jobTitle))
                }
            }

            is JobDetailsIntent.ShareInvoiceOnWhatsapp -> {
                val job = _state.value.jobModel ?: return
                val client = _state.value.clientModel
                val worker = _state.value.workerProfileModel
                val labour = _state.value.jobLabourItems
                val materials = _state.value.jobMaterialItems
                val payments = _state.value.jobPayments
                val whatsappText = InvoiceHtmlBuilder.buildWhatsappText(
                    job = job,
                    client = client,
                    workerProfile = worker,
                    labourItems = labour,
                    materialItems = materials,
                    payments = payments,
                    currencySymbol = event.currencySymbol
                )
                viewModelScope.launch {
                    _effect.emit(JobDetailsEffect.ShareTextOnWhatsapp(whatsappText))
                }
            }

            is JobDetailsIntent.AddPayment -> {
                viewModelScope.launch {
                    val payment = JobPaymentModel(
                        id = UuidGenerator.generate(),
                        jobId = jobId,
                        amount = event.amount,
                        paymentMethod = event.paymentMethod,
                        paymentDate = event.date,
                        note = event.note,
                        createdAt = EpochUtils.now()
                    )
                    addJobPaymentUseCase(payment)
                }
            }

            is JobDetailsIntent.DeletePayment -> {
                viewModelScope.launch {
                    deleteJobPaymentUseCase(event.paymentId)
                }
            }

            is JobDetailsIntent.ToggleProDialog -> {
                _state.update { it.copy(showProDialog = event.isOpen, proDialogFeatureName = event.featureName) }
                if (!event.isOpen && event.featureName == "PAYWALL") {
                    viewModelScope.launch {
                        _effect.emit(NavigateToPaywall)
                    }
                }
            }
        }
    }

    private fun loadJob() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getJobDetailsUseCase.invoke(id = jobId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(jobModel = result.data) }

                        result.data?.clientId?.let { clientId ->
                            // Suspend directly — no nested launch
                            loadClient(clientId)
                        }

                        _state.update { it.copy(isLoading = false) }
                    }

                    is Result.Error -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                jobModel = null
                            )
                        }
                        _effect.emit(JobDetailsEffect.ShowError(message = result.error.asString()))
                    }
                }
            }
        }
    }


    // Now a suspend fun — no viewModelScope.launch inside
    private suspend fun loadClient(clientId: String) {
        when (val result = getClientUseCase(clientId = clientId)) {
            is Result.Success -> _state.update { it.copy(clientModel = result.data) }
            is Result.Error -> _effect.emit(JobDetailsEffect.ShowError(result.error.asString()))
        }
    }

    private fun loadJobLabourItem() {
        viewModelScope.launch {
            getJobLabourItemUseCase(jobId = jobId).collectLatest {result ->
                when(result) {
                    is Result.Error -> {_effect.emit(JobDetailsEffect.ShowError(result.error.asString()))}
                    is Result.Success -> {_state.update { it.copy(jobLabourItems = result.data) }}
                }
            }
        }
    }

    private fun loadJobMaterialItem() {
        viewModelScope.launch {
            getJobMaterialItemsUseCase(jobId = jobId).collectLatest {result ->
                when(result) {
                    is Result.Error -> {_effect.emit(JobDetailsEffect.ShowError(result.error.asString()))}
                    is Result.Success -> {_state.update { it.copy(jobMaterialItems = result.data) }}
                }
            }
        }
    }

    private fun loadWorkerProfile() {
        viewModelScope.launch {
            getWorkerProfileUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(workerProfileModel = result.data) }
                    }
                    is Result.Error -> {
                        // Suppress profile load error for job details
                    }
                }
            }
        }
    }

    private fun observeProStatus() {
        viewModelScope.launch {
            observeProStatusUseCase().collectLatest { proStatus ->
                _state.update { it.copy(isPro = proStatus.hasProAccess) }
            }
        }
    }

    private fun loadPayments() {
        viewModelScope.launch {
            getJobPaymentsUseCase(jobId = jobId).collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(jobPayments = result.data) }
                    }
                    is Result.Error -> {
                        _effect.emit(ShowError(result.error.asString()))
                    }
                }
            }
        }
    }
}