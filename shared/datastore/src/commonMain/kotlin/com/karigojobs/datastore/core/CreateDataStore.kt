package com.karigojobs.datastore.core

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences


/**
 * @author hazratummar
 * Created on 21/05/26
 */

expect fun createDataStore(producePath: () -> String) : DataStore<Preferences>