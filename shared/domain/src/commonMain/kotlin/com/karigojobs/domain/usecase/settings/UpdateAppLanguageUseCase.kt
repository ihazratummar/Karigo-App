package com.karigojobs.domain.usecase.settings

import com.karigojobs.datastore.store.SettingsStore
import com.karigojobs.share.model.AppLanguage

class UpdateAppLanguageUseCase(
    private val settingsStore: SettingsStore
) {
    suspend operator fun invoke(language: AppLanguage) {
        settingsStore.setAppLanguage(language)
    }
}
