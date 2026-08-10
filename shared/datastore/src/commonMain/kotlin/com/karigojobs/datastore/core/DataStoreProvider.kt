package com.karigojobs.datastore.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


/**
 * @author hazratummar
 * Created on 21/05/26
 */

class DataStoreProvider(
    private val baseDir: String
) {

    val onboarding : DataStore<Preferences> by lazy {
        createDataStore { "$baseDir/onboarding.preferences_pb" }
    }

    val settings : DataStore<Preferences> by lazy {
        createDataStore { "$baseDir/settings.preferences_pb" }
    }

    val monetization : DataStore<Preferences> by lazy {
        createDataStore { "$baseDir/monetization.preferences_pb" }
    }

}