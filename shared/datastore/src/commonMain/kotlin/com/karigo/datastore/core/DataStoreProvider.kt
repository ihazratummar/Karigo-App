package com.karigo.datastore.core

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

}