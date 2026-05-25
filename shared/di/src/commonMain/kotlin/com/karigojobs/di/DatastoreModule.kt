package com.karigojobs.di

import com.karigojobs.datastore.core.DataStoreProvider
import com.karigojobs.datastore.store.OnboardingStore
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */
 

fun getDataStoreModule(): Module = module {
    single { DataStoreProvider(baseDir = get()) }
    single { OnboardingStore(get<DataStoreProvider>().onboarding) }
}