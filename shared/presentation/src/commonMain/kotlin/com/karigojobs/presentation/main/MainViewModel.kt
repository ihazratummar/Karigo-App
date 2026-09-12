package com.karigojobs.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.analytics.AnalyticsEvent
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.usecase.monetization.ObserveProStatusUseCase
import com.karigojobs.domain.usecase.settings.AppPreferences
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ProStatus
import com.karigojobs.share.model.ThemePreference
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    getAppPreferencesUseCase: GetAppPreferencesUseCase,
    observeProStatusUseCase: ObserveProStatusUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    init {
        analytics.logEvent(AnalyticsEvent.Event.APP_LAUNCHED)
    }

    val appPreferences: StateFlow<AppPreferences> = getAppPreferencesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppPreferences(
                theme = ThemePreference.SYSTEM,
                language = AppLanguage.ENGLISH,
                currency = "₹"
            )
        )

    val proStatus: StateFlow<ProStatus?> = observeProStatusUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = null
        )
}
