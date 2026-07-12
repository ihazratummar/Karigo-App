package com.karigojobs.presentation.estimate.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojob.share.utils.DateFormat
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojob.share.utils.formatNumber
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.domain.usecase.estimate.DeleteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateByIdUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateMaterialsUseCase
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.presentation.job.details.InvoiceHtmlBuilder
import com.karigojobs.presentation.job.details.PrintItem
import com.karigojobs.presentation.job.details.SubtotalItem
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
 * @author hazratummar
 * Created on 15/06/26
 */

class EstimateDetailsViewModel(
    private val estimateId: String,
    private val getEstimateByIdUseCase: GetEstimateByIdUseCase,
    private val getEstimateMaterialsUseCase: GetEstimateMaterialsUseCase,
    private val deleteEstimateUseCase: DeleteEstimateUseCase,
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {


    private val _state = MutableStateFlow(EstimateDetailsState())
    val state: StateFlow<EstimateDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EstimateDetailsEffect>(replay = 0)
    val effect: SharedFlow<EstimateDetailsEffect> = _effect.asSharedFlow()


    init {
        analytics.logScreenView(AnalyticsEvent.Screen.ESTIMATE_DETAILS)
        loadEstimate()
        loadEstimateMaterial()
        loadWorkerProfile()
    }

    fun onEvent(event: EstimateDetailsEvent) {
        when (event) {
            is EstimateDetailsEvent.DeleteEstimate -> {
                viewModelScope.launch {
                    val result = deleteEstimateUseCase(id = estimateId)
                    when (result) {
                        is Result.Error -> {
                            _state.update { it.copy(isDeleting = false) }
                            _effect.emit(EstimateDetailsEffect.ShowError(result.error.asString()))
                        }

                        is Result.Success<*> -> {
                            _state.update { it.copy(isDeleting = false) }
                            _effect.emit(EstimateDetailsEffect.NavigationBack)
                        }
                    }
                }
            }

            EstimateDetailsEvent.WhatsAppShare -> {
                val estimate = state.value.estimateDetails ?: return
                val materials = state.value.siteEstimateMaterial
                
                val message = buildString {
                    append("*${estimate.projectTitle} — ${estimate.clientName}*")
                    append("\nDate: ${estimate.date.toReadableDate(DateFormat.DATE_ONLY)}")
                    if (!estimate.siteNote.isNullOrBlank()) {
                        append("\nNote: ${estimate.siteNote}")
                    }
                    append("\n\n*Materials Required:*")
                    materials.forEachIndexed { index, material ->
                        append("\n${index + 1}. ${material.materialName} — ${material.quantity.formatNumber()} ${material.unit}")
                        if (estimate.showRate) {
                            append(" — ₹${material.total.formatNumber()}")
                        }
                    }
                    
                    if (estimate.showRate) {
                        append("\n\n*Estimated Total: ₹${estimate.total?.formatNumber()}*")
                    }
                    append("\n\n_Sent from Karigo_")
                }
                
                viewModelScope.launch {
                    _effect.emit(EstimateDetailsEffect.ShareToWhatsApp(message))
                }
            }
            is EstimateDetailsEvent.ToggleDelete -> {
                _state.update { it.copy(isDeleting = event.isOpen) }
            }

            is EstimateDetailsEvent.GenerateEstimatePdf -> {
                val estimate = state.value.estimateDetails ?: return
                val materials = state.value.siteEstimateMaterial
                val worker = state.value.workerProfileModel
                val client = state.value.clientModel

                val items = materials.map {
                    PrintItem(
                        name = it.materialName,
                        subtitle = "Material",
                        quantity = it.quantity,
                        unit = it.unit,
                        rate = it.rate,
                        total = it.total
                    )
                }
                
                val subtotals = if (estimate.showRate) {
                    listOf(SubtotalItem("Materials Subtotal", estimate.total ?: 0.0))
                } else {
                    emptyList()
                }

                val html = InvoiceHtmlBuilder.buildGenericHtml(
                    title = "ESTIMATE",
                    documentNumber = "EST-${estimate.id.takeLast(6).uppercase()}",
                    dateString = estimate.date.toReadableDate(DateFormat.DATE_ONLY),
                    statusText = null,
                    statusColor = null,
                    workerProfile = worker,
                    client = client,
                    clientNameFallback = estimate.clientName,
                    items = items,
                    subtotals = subtotals,
                    grandTotal = estimate.total ?: 0.0,
                    currencySymbol = event.currencySymbol,
                    description = null,
                    notes = estimate.siteNote,
                    showRate = estimate.showRate
                )

                viewModelScope.launch {
                    _effect.emit(EstimateDetailsEffect.ShareEstimatePdf(html = html, estimateTitle = "Estimate_${estimate.projectTitle.replace(" ", "_")}"))
                }
            }

            is EstimateDetailsEvent.ShareEstimateOnWhatsapp -> {
                val estimate = state.value.estimateDetails ?: return
                val materials = state.value.siteEstimateMaterial
                
                val message = buildString {
                    append("*${estimate.projectTitle} — ${estimate.clientName}*")
                    append("\nDate: ${estimate.date.toReadableDate(DateFormat.DATE_ONLY)}")
                    if (!estimate.siteNote.isNullOrBlank()) {
                        append("\nNote: ${estimate.siteNote}")
                    }
                    append("\n\n*Materials Required:*")
                    materials.forEachIndexed { index, material ->
                        append("\n${index + 1}. ${material.materialName} — ${material.quantity.formatNumber()} ${material.unit}")
                        if (estimate.showRate) {
                            append(" — ${event.currencySymbol}${material.total.formatNumber()}")
                        }
                    }
                    
                    if (estimate.showRate) {
                        append("\n\n*Estimated Total: ${event.currencySymbol}${estimate.total?.formatNumber()}*")
                    }
                    append("\n\n_Sent from Karigo_")
                }
                
                viewModelScope.launch {
                    _effect.emit(EstimateDetailsEffect.ShareTextOnWhatsapp(message))
                }
            }
        }
    }

    private fun loadEstimate() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getEstimateByIdUseCase(estimateId = estimateId).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        if (result.error != SiteEstimateError.NotFound) {
                            _effect.emit(EstimateDetailsEffect.ShowError(result.error.asString()))
                        }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                estimateDetails = result.data
                            )
                        }
                        result.data?.clientId?.let { loadClient(it) }
                    }
                }
            }
        }
    }

    private fun loadEstimateMaterial() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            getEstimateMaterialsUseCase(estimateId = estimateId).collectLatest { result ->
                when (result) {
                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        if (result.error != SiteEstimateError.NotFound) {
                            _effect.emit(EstimateDetailsEffect.ShowError(result.error.asString()))
                        }
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                siteEstimateMaterial = result.data
                            )
                        }
                    }
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
                        // Suppress
                    }
                }
            }
        }
    }

    private fun loadClient(clientId: String) {
        viewModelScope.launch {
            val result = getClientUseCase(clientId)
            if (result is Result.Success) {
                _state.update { it.copy(clientModel = result.data) }
            }
        }
    }
}