package com.karigojobs.di

import android.content.Context
import com.karigojobs.shared.database.SqlDeriverFactory
import org.koin.core.module.Module

actual fun Module.platformSqlDriverFactory() {
    single { SqlDeriverFactory(get<Context>()) }
}
