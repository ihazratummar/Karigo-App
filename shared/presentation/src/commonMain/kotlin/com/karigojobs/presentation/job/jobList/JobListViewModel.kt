package com.karigojobs.presentation.job.jobList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.GetAllJobUseCase
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
 * Created on 25/05/26
 */

class JobListViewModel (
    private val getAllJobUseCase: GetAllJobUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(JobListState())
    val state : StateFlow<JobListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<JobListEffect>(replay = 0)
    val effect: SharedFlow<JobListEffect> = _effect.asSharedFlow()


    init {
        loadAllJob()
    }



    fun onEvent(event: JobListIntent){
        when(event){
            is JobListIntent.JobClick -> TODO()
            is JobListIntent.JobFilterClick -> TODO()
            is JobListIntent.SearchTextChanged -> {
                _state.update {
                    it.copy(
                        searchJobText = event.text
                    )
                }
            }
        }
    }

    private fun loadAllJob() {
        _state.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            getAllJobUseCase().collectLatest { result ->
                when (result) {
                    is Result.Success -> {
                        _state.update { it.copy(jobs = result.data, isLoading = false) }
                        println("Jobs Data -> $result.data")
                    }

                    is Result.Error -> {
                        _state.update { it.copy(isLoading = false) }
                        _effect.emit(JobListEffect.Error(message = result.error.asString()))
                    }
                }
            }
        }
    }

}