package com.karigojobs.datastore.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class SettingsStore(
    private val dataStore: DataStore<Preferences>
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    companion object Keys {
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
        val AUTO_BACKUP_ENABLED = androidx.datastore.preferences.core.booleanPreferencesKey("auto_backup_enabled")
    }

    val themePreference: StateFlow<ThemePreference> =
        dataStore.data.map { pref ->
            val themeString = pref[APP_THEME]
            if (themeString == null) ThemePreference.SYSTEM else runCatching { ThemePreference.valueOf(themeString) }.getOrDefault(ThemePreference.SYSTEM)
        }.stateIn(scope, SharingStarted.Eagerly, ThemePreference.SYSTEM)

    val appLanguage: StateFlow<AppLanguage> =
        dataStore.data.map { pref ->
            val langString = pref[APP_LANGUAGE]
            if (langString == null) AppLanguage.ENGLISH else runCatching { AppLanguage.valueOf(langString) }.getOrDefault(AppLanguage.ENGLISH)
        }.stateIn(scope, SharingStarted.Eagerly, AppLanguage.ENGLISH)
        
    val autoBackupEnabled: StateFlow<Boolean> = 
        dataStore.data.map { pref ->
            pref[AUTO_BACKUP_ENABLED] ?: false
        }.stateIn(scope, SharingStarted.Eagerly, false)

    suspend fun setThemePreference(theme: ThemePreference) {
        dataStore.edit { pref ->
            pref[APP_THEME] = theme.name
        }
    }

    suspend fun setAppLanguage(language: AppLanguage) {
        dataStore.edit { pref ->
            pref[APP_LANGUAGE] = language.name
        }
    }
    
    suspend fun setAutoBackupEnabled(enabled: Boolean) {
        dataStore.edit { pref ->
            pref[AUTO_BACKUP_ENABLED] = enabled
        }
    }
}
