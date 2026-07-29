package com.karigojobs.domain.usecase.settings

import com.karigojobs.datastore.store.SettingsStore

class UpdateAppCurrencyUseCase(
    private val settingsStore: SettingsStore
) {
    suspend operator fun invoke(currency: String) {
        settingsStore.setAppCurrency(currency)
    }
}
