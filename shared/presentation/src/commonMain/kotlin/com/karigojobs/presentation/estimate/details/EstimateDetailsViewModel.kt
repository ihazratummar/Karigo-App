package com.karigojobs.presentation.estimate.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojob.share.utils.DateFormat
import com.karigojob.share.utils.DateUtils.toReadableDate
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.SiteEstimateError
import com.karigojobs.domain.usecase.estimate.DeleteEstimateUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateByIdUseCase
import com.karigojobs.domain.usecase.estimate.GetEstimateMaterialsUseCase
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
 * @author hazratummar
 * Created on 15/06/26
 */

class EstimateDetailsViewModel(
    private val estimateId: String,
    private val getEstimateByIdUseCase: GetEstimateByIdUseCase,
    private val getEstimateMaterialsUseCase: GetEstimateMaterialsUseCase,
    private val deleteEstimateUseCase: DeleteEstimateUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(EstimateDetailsState())
    val state: StateFlow<EstimateDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<EstimateDetailsEffect>(replay = 0)
    val effect: SharedFlow<EstimateDetailsEffect> = _effect.asSharedFlow()


    init {
        loadEstimate()
        loadEstimateMaterial()
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
                        append("\n${index + 1}. ${material.materialName} — ${material.quantity} ${material.unit}")
                        if (estimate.showRate) {
                            append(" — ₹${material.total}")
                        }
                    }
                    
                    if (estimate.showRate) {
                        append("\n\n*Estimated Total: ₹${estimate.total}*")
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


}