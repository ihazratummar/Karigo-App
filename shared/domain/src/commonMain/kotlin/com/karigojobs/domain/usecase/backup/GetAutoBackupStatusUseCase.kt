package com.karigojobs.domain.usecase.backup

import com.karigojobs.datastore.store.SettingsStore
import kotlinx.coroutines.flow.Flow

class GetAutoBackupStatusUseCase(
    private val settingsStore: SettingsStore
) {
    operator fun invoke(): Flow<Boolean> {
        return settingsStore.autoBackupEnabled
    }
}
