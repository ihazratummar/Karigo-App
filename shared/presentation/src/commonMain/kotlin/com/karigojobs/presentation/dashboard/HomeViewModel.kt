package com.karigojobs.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.usecase.GetSelectedTradeTypeUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


/**
 * @author hazratummar
 * Created on 23/05/26
 */

class HomeViewModel(
    private val getSelectedTradeTypeUseCase: GetSelectedTradeTypeUseCase
) : ViewModel() {


    private val _state  = MutableStateFlow(HomeState())
    val state : StateFlow<HomeState> = _state.asStateFlow()


    init {
        loadSelectedTrade()
    }


    private fun loadSelectedTrade(){
        viewModelScope.launch {
            getSelectedTradeTypeUseCase.invoke().collectLatest { trades ->
                _state.update {
                    it.copy(
                        selectedTrades = trades
                    )
                }
            }
        }
    }
}