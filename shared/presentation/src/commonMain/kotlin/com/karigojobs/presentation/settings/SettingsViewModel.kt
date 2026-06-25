package com.karigojobs.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.settings.GetWorkerProfileUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * @author hazratummar
 * Created on 24/06/26
 */

class SettingsViewModel (
    private val getWorkerProfileUseCase: GetWorkerProfileUseCase
): ViewModel() {


    private val _state = MutableStateFlow(SettingsState())
    val state : StateFlow<SettingsState> = _state.asStateFlow()



    init {
        loadWorker()
    }

    private fun loadWorker() {
        viewModelScope.launch {
            getWorkerProfileUseCase().collectLatest { result ->
                when(result){
                    is Result.Error -> {}
                    is Result.Success -> {
                        _state.update { it.copy(workerProfileModel = result.data) }
                    }
                }
            }
        }
    }



}