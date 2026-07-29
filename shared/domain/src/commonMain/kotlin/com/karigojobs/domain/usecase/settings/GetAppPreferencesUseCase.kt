package com.karigojobs.domain.usecase.settings

import com.karigojobs.datastore.store.SettingsStore
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class GetAppPreferencesUseCase(
    private val settingsStore: SettingsStore
) {
    data class AppPreferences(
        val theme: ThemePreference,
        val language: AppLanguage,
        val currency: String
    )

    operator fun invoke(): Flow<AppPreferences> {
        return combine(
            settingsStore.themePreference,
            settingsStore.appLanguage,
            settingsStore.appCurrency
        ) { theme, language, currency ->
            AppPreferences(theme, language, currency)
        }
    }

    fun getSync(): AppPreferences {
        return AppPreferences(
            theme = settingsStore.themePreference.value,
            language = settingsStore.appLanguage.value,
            currency = settingsStore.appCurrency.value
        )
    }
}
