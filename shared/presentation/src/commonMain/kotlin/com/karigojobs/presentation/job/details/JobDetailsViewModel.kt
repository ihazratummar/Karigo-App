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
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.presentation.job.details.JobDetailsEffect.*
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
 * Created on 26/05/26
 */

class JobDetailsViewModel(
    private val jobId: String,
    private val getJobDetailsUseCase: GetJobDetailsUseCase,
    private val changeJobStatusUseCase: ChangeJobStatusUseCase,
    private val deleteJobUseCase: DeleteJobUseCase,
    private val getClientUseCase: GetClientUseCase,
    private val getJobLabourItemUseCase: GetJobLabourItemUseCase,
    private val getJobMaterialItemsUseCase: GetJobMaterialItemsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JobDetailsState())
    val state: StateFlow<JobDetailsState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<JobDetailsEffect>(replay = 0)
    val effect: SharedFlow<JobDetailsEffect> = _effect.asSharedFlow()


    init {
        loadJob()
        loadJobLabourItem()
        loadJobMaterialItem()
    }


    fun onEven(event: JobDetailsIntent) {
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
}