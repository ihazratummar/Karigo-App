package com.karigojobs.di

import com.karigojobs.shared.database.SqlDeriverFactory
import org.koin.core.module.Module

actual fun Module.platformSqlDriverFactory() {
    single { SqlDeriverFactory() }
}
