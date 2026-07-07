package com.karigojobs.domain.usecase.backup

import com.karigojobs.datastore.store.SettingsStore

class SetAutoBackupStatusUseCase(
    private val settingsStore: SettingsStore
) {
    suspend operator fun invoke(enabled: Boolean) {
        settingsStore.setAutoBackupEnabled(enabled)
    }
}
