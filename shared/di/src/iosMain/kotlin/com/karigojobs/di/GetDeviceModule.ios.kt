package com.karigojobs.di

import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.shared.device.ContactProviderImpl
import com.karigojobs.shared.device.NetworkMonitor
import com.karigojobs.shared.device.NetworkMonitorImpl
import com.karigojobs.data.repository.AppPathProvider
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDeviceModule(): Module = module {
    single<DeviceContactProvider> { ContactProviderImpl() }
    single { AppPathProvider() }
    single<com.karigojobs.data.billing.PlatformBillingProvider> { com.karigojobs.data.billing.PlatformBillingProviderImpl(monetizationStore = get()) }
    single<NetworkMonitor> { NetworkMonitorImpl() }
}

actual fun getAnalyticsLogger(): com.karigojobs.domain.analytics.AnalyticsLogger = object : com.karigojobs.domain.analytics.AnalyticsLogger {
    override fun logEvent(eventName: String, params: Map<String, Any>) {}
    override fun logScreenView(screenName: String, screenClass: String?) {}
}

