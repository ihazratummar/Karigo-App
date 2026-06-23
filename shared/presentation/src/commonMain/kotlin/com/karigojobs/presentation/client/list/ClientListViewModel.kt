package com.karigojobs.presentation.client.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.result.Result
import com.karigojobs.domain.usecase.client.GetClientListUseCase
import com.karigojobs.presentation.erroMap.asString
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
 * Created on 23/06/26
 */

class ClientListViewModel(
    private val getAllClientListUseCase: GetClientListUseCase
) : ViewModel() {


    private val _state = MutableStateFlow(ClientListState())
    val state : StateFlow<ClientListState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<ClientListEffect>(replay = 0)
    val effect : SharedFlow<ClientListEffect> = _effect.asSharedFlow()


    init {
        observeClient()
    }


    fun onEvent(event: ClientListEvent) {

    }

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun observeClient() {
        viewModelScope.launch {
            _state.map { it.searchClient }
                .distinctUntilChanged()
                .debounce(200.milliseconds)
                .flatMapLatest { query->
                    _state.update { it.copy(isLoading = true) }
                    getAllClientListUseCase(query = query)
                }.collectLatest { result ->
                    _state.update { it.copy(isLoading = false) }
                    when(result){
                        is Result.Error ->  {
                            _effect.emit(ClientListEffect.ShowError(result.error.asString()))
                        }
                        is Result.Success -> {
                            _state.update { it.copy(clients = result.data) }
                        }
                    }
                }
        }
    }

}