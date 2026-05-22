package com.karigo.di

import com.karigo.datastore.core.DataStoreProvider
import com.karigo.datastore.store.OnboardingStore
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