package com.karigo.di

import app.cash.sqldelight.ColumnAdapter
import app.cash.sqldelight.db.SqlDriver
import com.karigo.shared.database.KarigoDatabase
import com.karigo.shared.database.Materials
import com.karigo.shared.database.SqlDeriverFactory
import org.koin.core.module.Module
import org.koin.dsl.module
import kotlin.time.Instant

fun getDatabaseModule(): Module = module {

    // 1. factory first — needs Context (provided by KarigoApp)
    single { SqlDeriverFactory(get()) }

    // 2. driver from factory
    single<SqlDriver> { get<SqlDeriverFactory>().getSqlDriver() }

    // 3. adapter
    single {
        Materials.Adapter(
            created_atAdapter = object : ColumnAdapter<Instant, Long> {
                override fun decode(databaseValue: Long): Instant =
                    Instant.fromEpochMilliseconds(databaseValue)

                override fun encode(value: Instant): Long =
                    value.toEpochMilliseconds()
            }
        )
    }

    // 4. database last — needs driver + adapter
    single { KarigoDatabase(driver = get(), materialsAdapter = get()) }
}