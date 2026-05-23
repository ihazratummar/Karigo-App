package com.karigo.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.karigo.shared.database.KarigoDatabase
import com.karigo.shared.database.Materials
import com.karigo.shared.database.SqlDeriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.time.Instant

expect fun Module.platformSqlDriverFactory()

fun getDatabaseModule(): Module = module {

    // 1. factory first — needs Context (provided by KarigoApp)
    platformSqlDriverFactory()

    // 2. driver from factory
    single<SqlDriver> { get<SqlDeriverFactory>().getSqlDriver() }

    // 3. database last — needs driver + adapter
    single { KarigoDatabase(driver = get()) }
}