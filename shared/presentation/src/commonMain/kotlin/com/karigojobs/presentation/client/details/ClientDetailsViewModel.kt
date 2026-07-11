package com.karigojobs.presentation.client.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.client.DeleteClientUseCase
import com.karigojobs.domain.usecase.client.GetClientFlowUseCase
import com.karigojobs.domain.usecase.client.GetClientUseCase
import com.karigojobs.domain.usecase.job.GetJobByClientUseCase
import com.karigojobs.presentation.client.details.ClientDetailsEffect.*
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
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
 * Created on 23/06/26
 */

class ClientDetailsViewModel(
    private val clientId: String,
    private val getClientUseCase: GetClientFlowUseCase,
    private val getJobByClientUseCase: GetJobByClientUseCase,
    private val deleteClientUseCase: DeleteClientUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {


    private val _state = MutableStateFlow(ClientDetailsState())
    val state: StateFlow<ClientDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ClientDetailsEffect>(replay = 0)
    val effect: SharedFlow<ClientDetailsEffect> = _effect.asSharedFlow()

    init {
        analytics.logScreenView(AnalyticsEvent.Screen.CLIENT_DETAILS)
        loadClient()
        loadJobByClient()
    }

    fun onEvent(event: ClientDetailsEvent) {
        when (event) {
            ClientDetailsEvent.CallClient -> {
                viewModelScope.launch {
                    val phone = _state.value.client?.phone
                    _effect.emit(CallClient(phone.toString()))
                }
            }

            ClientDetailsEvent.OpenClientWhatsApp -> {
                viewModelScope.launch {
                    val phone = _state.value.client?.phone
                    _effect.emit(OpenWhatsApp(phone.toString()))
                }
            }

            is ClientDetailsEvent.ToggleDelete -> {
                _state.update { it.copy(isDeleting = event.isOpen) }
            }

            ClientDetailsEvent.DeleteClient -> {
                viewModelScope.launch {
                    val result  = deleteClientUseCase(clientId = clientId)
                    when(result){
                        is Result.Error -> {
                            _state.update { it.copy(isDeleting = false) }
                            _effect.emit(ClientDetailsEffect.ShowError(message = result.error.asString()))
                        }
                        is Result.Success -> {
                            _state.update { it.copy(isDeleting = false) }
                            _effect.emit(NavBack)
                        }
                    }
                }
            }
        }
    }

    private fun loadClient() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getClientUseCase(clientId = clientId).collectLatest { result ->
                _state.update { it.copy(isLoading = true) }
                when (result) {
                    is Result.Error -> {
                        _effect.emit(ClientDetailsEffect.ShowError(result.error.asString()))
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                client = result.data
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadJobByClient() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getJobByClientUseCase(clientId = clientId).collectLatest { result ->
                _state.update { it.copy(isLoading = false) }
                when (result) {
                    is Result.Error -> {
                        _effect.emit(ClientDetailsEffect.ShowError(result.error.asString()))
                    }

                    is Result.Success -> {
                        _state.update {
                            it.copy(
                                jobHistory = result.data
                            )
                        }
                    }
                }
            }
        }
    }

}