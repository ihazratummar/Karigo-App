package com.karigojobs.di

import com.karigojobs.datastore.core.DataStoreProvider
import com.karigojobs.datastore.store.OnboardingStore
import com.karigojobs.datastore.store.SettingsStore
import com.karigojobs.datastore.store.MonetizationStore
import org.koin.core.module.Module
import org.koin.dsl.module

fun getDataStoreModule(): Module = module {
    single { DataStoreProvider(baseDir = get()) }
    single { OnboardingStore(get<DataStoreProvider>().onboarding) }
    single { SettingsStore(get<DataStoreProvider>().settings) }
    single { MonetizationStore(get<DataStoreProvider>().monetization) }
}