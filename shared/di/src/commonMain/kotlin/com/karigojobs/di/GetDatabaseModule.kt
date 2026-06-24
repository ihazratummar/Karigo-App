package com.karigojobs.di

import app.cash.sqldelight.db.SqlDriver
import com.karigojobs.shared.database.KarigojobsDatabase
import com.karigojobs.shared.database.SqlDeriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module

expect fun Module.platformSqlDriverFactory()

fun getDatabaseModule(): Module = module {

    // 1. factory first — needs Context (provided by KarigojobsApp)
    platformSqlDriverFactory()

    // 2. driver from factory
    single<SqlDriver> { get<SqlDeriverFactory>().getSqlDriver() }

    // 3. database last — needs driver + adapter
    single { KarigojobsDatabase(driver = get()) }
}