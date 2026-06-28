package com.karigojobs.domain.usecase.settings

import com.karigojobs.datastore.store.SettingsStore
import com.karigojobs.share.model.ThemePreference

class UpdateThemePreferenceUseCase(
    private val settingsStore: SettingsStore
) {
    suspend operator fun invoke(theme: ThemePreference) {
        settingsStore.setThemePreference(theme)
    }
}
