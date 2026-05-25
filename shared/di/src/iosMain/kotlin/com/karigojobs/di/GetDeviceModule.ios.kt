package com.karigojobs.di

import com.karigojobs.domain.repository.DeviceContactProvider
import com.karigojobs.shared.device.ContactProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDeviceModule(): Module = module {
    single<DeviceContactProvider> { ContactProviderImpl() }
}

