package com.karigojobs.datastore.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.karigojobs.share.model.TradeType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


/**
 * @author hazratummar
 * Created on 21/05/26
 */

class OnboardingStore(
    private val dataStore: DataStore<Preferences>
) {

    companion object Keys {
        val IS_COMPLETE = booleanPreferencesKey("is_complete")
        val SELECTED_TRADES = stringPreferencesKey("selected_trades")
    }

    // observe as Flow - reacts to changes

    val isComplete : Flow<Boolean> =
        dataStore.data.map { pref ->
            pref[IS_COMPLETE] ?: false
        }

    val selectedTrades : Flow<Set<TradeType>> =
        dataStore.data.map { pref ->
            pref[SELECTED_TRADES]
                ?.split(",")
                ?.mapNotNull { runCatching { TradeType.valueOf(it) }.getOrNull() }
                ?.toSet()
                ?: emptySet()
        }

    suspend fun markComplete(trades : Set<TradeType>) {
        dataStore.edit { pref ->
            pref[IS_COMPLETE] = true
            pref[SELECTED_TRADES] = trades.joinToString(",") {it.name}
        }
    }

    suspend fun saveTrades(trades: Set<TradeType>) {
        dataStore.edit {pref ->
            pref[SELECTED_TRADES] = trades.joinToString(","){it.name}
        }
    }

}