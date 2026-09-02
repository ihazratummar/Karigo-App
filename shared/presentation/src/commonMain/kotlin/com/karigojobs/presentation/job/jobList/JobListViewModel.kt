package com.karigojobs.presentation.job.jobList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.job.GetAllJobUseCase
import com.karigojobs.domain.usecase.job.SearchJobUseCase
import com.karigojobs.share.model.JobModel
import com.karigojobs.presentation.erroMap.asString
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds


/**
 * @author hazratummar
 * Created on 25/05/26
 */

class JobListViewModel (
    private val getAllJobUseCase: GetAllJobUseCase,
    private val searchJobUseCase: SearchJobUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    private val _state = MutableStateFlow(JobListState())
    val state : StateFlow<JobListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<JobListEffect>(replay = 0)
    val effect: SharedFlow<JobListEffect> = _effect.asSharedFlow()


    init {
        analytics.logScreenView(AnalyticsEvent.Screen.JOB_LIST)
        observeSearchText()
    }


    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    private fun observeSearchText() {
        viewModelScope.launch {
            _state.map { it.searchJobText }
                .distinctUntilChanged()
                .debounce(300L.milliseconds)
                .flatMapLatest { query ->
                    _state.update { it.copy(isLoading = true) }
                    if (query.isBlank()) {
                        getAllJobUseCase()
                    } else {
                        searchJobUseCase(query)
                    }
                }.collectLatest { result ->
                    when (result) {
                        is Result.Success<List<JobModel>> -> {
                            _state.update { it.copy(jobs = result.data, isLoading = false) }
                        }

                        is Result.Error -> {
                            _state.update { it.copy(isLoading = false) }
                            _effect.emit(JobListEffect.Error(message = result.error.asString()))
                        }
                    }
                }
        }
    }

    fun onEvent(event: JobListIntent){
        when(event){
            is JobListIntent.JobClick -> {
                viewModelScope.launch {
                    _effect.emit(JobListEffect.OnJobClick(event.jobId))
                }
            }
            is JobListIntent.JobFilterClick -> {
                _state.update { it.copy(jobStatusFilter = event.filter) }
            }
            is JobListIntent.SearchTextChanged -> {
                _state.update {
                    it.copy(
                        searchJobText = event.text
                    )
                }
            }
        }
    }

}