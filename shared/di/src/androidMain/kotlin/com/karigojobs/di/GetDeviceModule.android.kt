package com.karigojobs.di

import com.karigojobs.data.analytics.FirebaseAnalyticsLogger
import com.karigojobs.data.billing.PlatformBillingProvider
import com.karigojobs.data.billing.PlatformBillingProviderImpl
import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.shared.device.ContactProviderImpl
import com.karigojobs.shared.device.NetworkMonitor
import com.karigojobs.shared.device.NetworkMonitorImpl
import com.karigojobs.data.repository.AppPathProvider
import com.karigojobs.domain.analytics.AnalyticsLogger
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDeviceModule(): Module = module {
    single<DeviceContactProvider> { ContactProviderImpl(context = get()) }
    single { AppPathProvider(context = get()) }
    single<PlatformBillingProvider> { PlatformBillingProviderImpl(context = get(), monetizationStore = get()) }
    single<NetworkMonitor> { NetworkMonitorImpl(context = get()) }
    single<com.karigojobs.domain.repository.AppVersionProvider> { com.karigojobs.shared.device.AppVersionProviderImpl(context = get()) }
}

actual fun getAnalyticsLogger(): AnalyticsLogger = FirebaseAnalyticsLogger()