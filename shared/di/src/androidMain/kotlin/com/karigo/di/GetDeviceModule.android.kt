package com.karigo.di

import com.karigo.domain.repository.DeviceContactProvider
import com.karigo.shared.device.ContactProviderImpl
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun getDeviceModule(): Module = module {
    single<DeviceContactProvider> { ContactProviderImpl(context = get()) }
}