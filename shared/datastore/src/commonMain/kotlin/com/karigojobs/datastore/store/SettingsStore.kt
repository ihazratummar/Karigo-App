package com.karigojobs.datastore.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.karigojobs.share.model.AppLanguage
import com.karigojobs.share.model.ThemePreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsStore(
    private val dataStore: DataStore<Preferences>
) {

    companion object Keys {
        val APP_THEME = stringPreferencesKey("app_theme")
        val APP_LANGUAGE = stringPreferencesKey("app_language")
    }

    val themePreference: Flow<ThemePreference> =
        dataStore.data.map { pref ->
            val themeString = pref[APP_THEME]
            runCatching { ThemePreference.valueOf(themeString!!) }.getOrDefault(ThemePreference.SYSTEM)
        }

    val appLanguage: Flow<AppLanguage> =
        dataStore.data.map { pref ->
            val langString = pref[APP_LANGUAGE]
            runCatching { AppLanguage.valueOf(langString!!) }.getOrDefault(AppLanguage.ENGLISH)
        }

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
}
