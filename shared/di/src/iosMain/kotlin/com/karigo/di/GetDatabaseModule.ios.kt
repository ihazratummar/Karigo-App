package com.karigo.di

import com.karigo.shared.database.SqlDeriverFactory
import org.koin.core.module.Module

actual fun Module.platformSqlDriverFactory() {
    single { SqlDeriverFactory() }
}
