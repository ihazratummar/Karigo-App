package com.karigojobs.di

import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.shared.device.ContactProviderImpl
import com.karigojobs.data.repository.AppPathProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDeviceModule(): Module = module {
    single<DeviceContactProvider> { ContactProviderImpl(context = get()) }
    single { AppPathProvider(context = get()) }
}

actual fun getAnalyticsLogger(): com.karigojobs.domain.analytics.AnalyticsLogger = com.karigojobs.data.analytics.FirebaseAnalyticsLogger()