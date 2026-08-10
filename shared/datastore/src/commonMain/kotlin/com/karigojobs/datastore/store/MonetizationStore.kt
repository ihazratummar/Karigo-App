package com.karigojobs.datastore.store

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.karigojobs.share.model.MonthlyJobLimit
import com.karigojobs.share.model.PlanTier
import com.karigojobs.share.model.ProStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MonetizationStore(
    private val dataStore: DataStore<Preferences>
) {
    private val scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    companion object Keys {
        val IS_PRO_USER = booleanPreferencesKey("is_pro_user")
        val PLAN_TIER = stringPreferencesKey("plan_tier")
        val MONTHLY_JOB_COUNT = intPreferencesKey("monthly_job_count")
        val LAST_RESET_YEAR_MONTH = stringPreferencesKey("last_reset_year_month")
        val GOOGLE_ACCOUNT_ID = stringPreferencesKey("google_account_id")
        val LAST_SYNC_TIMESTAMP = longPreferencesKey("last_sync_timestamp")
        val PURCHASE_DATE_EPOCH_MS = longPreferencesKey("purchase_date_epoch_ms")
        val EXPIRATION_DATE_EPOCH_MS = longPreferencesKey("expiration_date_epoch_ms")
    }

    val proStatus: StateFlow<ProStatus> =
        dataStore.data.map { pref ->
            val isPro = pref[IS_PRO_USER] ?: false
            val tierStr = pref[PLAN_TIER]
            val tier = if (tierStr != null) runCatching { PlanTier.valueOf(tierStr) }.getOrDefault(PlanTier.FREE) else PlanTier.FREE
            val purchaseDate = pref[PURCHASE_DATE_EPOCH_MS]
            val expDate = pref[EXPIRATION_DATE_EPOCH_MS]
            ProStatus(
                isProActive = isPro,
                planTier = tier,
                purchaseDateEpochMs = purchaseDate,
                expirationDateEpochMs = expDate
            )
        }.stateIn(scope, SharingStarted.Eagerly, ProStatus())

    val monthlyJobLimit: StateFlow<MonthlyJobLimit> =
        dataStore.data.map { pref ->
            val usedCount = pref[MONTHLY_JOB_COUNT] ?: 0
            MonthlyJobLimit(usedJobsCount = usedCount)
        }.stateIn(scope, SharingStarted.Eagerly, MonthlyJobLimit())

    val lastResetYearMonth: StateFlow<String> =
        dataStore.data.map { pref ->
            pref[LAST_RESET_YEAR_MONTH] ?: ""
        }.stateIn(scope, SharingStarted.Eagerly, "")

    val googleAccountId: StateFlow<String> =
        dataStore.data.map { pref ->
            pref[GOOGLE_ACCOUNT_ID] ?: ""
        }.stateIn(scope, SharingStarted.Eagerly, "")

    suspend fun setProStatus(
        isPro: Boolean,
        tier: PlanTier = PlanTier.FREE,
        purchaseDateEpochMs: Long? = null,
        expirationDateEpochMs: Long? = null
    ) {
        dataStore.edit { pref ->
            pref[IS_PRO_USER] = isPro
            pref[PLAN_TIER] = tier.name
            pref[LAST_SYNC_TIMESTAMP] = getCurrentTimestamp()
            if (purchaseDateEpochMs != null) {
                pref[PURCHASE_DATE_EPOCH_MS] = purchaseDateEpochMs
            } else if (!isPro) {
                pref.remove(PURCHASE_DATE_EPOCH_MS)
            }
            if (expirationDateEpochMs != null) {
                pref[EXPIRATION_DATE_EPOCH_MS] = expirationDateEpochMs
            } else if (!isPro) {
                pref.remove(EXPIRATION_DATE_EPOCH_MS)
            }
        }
    }

    suspend fun setMonthlyJobCount(count: Int, yearMonth: String) {
        dataStore.edit { pref ->
            pref[MONTHLY_JOB_COUNT] = count
            pref[LAST_RESET_YEAR_MONTH] = yearMonth
        }
    }

    suspend fun setGoogleAccountId(id: String) {
        dataStore.edit { pref ->
            pref[GOOGLE_ACCOUNT_ID] = id
        }
    }

    private fun getCurrentTimestamp(): Long {
        return kotlin.math.abs(kotlin.random.Random.nextLong())
    }
}
