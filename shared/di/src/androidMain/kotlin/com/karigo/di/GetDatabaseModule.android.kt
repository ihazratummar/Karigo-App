package com.karigo.di

import android.content.Context
import com.karigo.shared.database.SqlDeriverFactory
import org.koin.core.module.Module

actual fun Module.platformSqlDriverFactory() {
    single { SqlDeriverFactory(get<Context>()) }
}
