package com.karigo.di

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 21/05/26
 */

fun initKoin(koinApplication : (KoinApplication.() -> Unit)? = null) {
    startKoin {
        koinApplication?.invoke(this)
        modules(
            getDataStoreModule(),
            getDatabaseModule(),
            getRepositoryModule(),
            getDomainModule(),
            getDeviceModule(),
            getPresentationModule()
        )
    }
}