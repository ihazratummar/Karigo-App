package com.karigojobs.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.karigojobs.domain.usecase.settings.GetAppPreferencesUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import com.karigojobs.domain.analytics.AnalyticsLogger
import com.karigojobs.domain.analytics.AnalyticsEvent

class MainViewModel(
    getAppPreferencesUseCase: GetAppPreferencesUseCase,
    private val analytics: AnalyticsLogger
) : ViewModel() {

    init {
        analytics.logEvent(AnalyticsEvent.Event.APP_LAUNCHED)
    }

    val appPreferences: StateFlow<GetAppPreferencesUseCase.AppPreferences> = getAppPreferencesUseCase()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = GetAppPreferencesUseCase.AppPreferences(
                theme = com.karigojobs.share.model.ThemePreference.SYSTEM,
                language = com.karigojobs.share.model.AppLanguage.ENGLISH
            )
        )
}
