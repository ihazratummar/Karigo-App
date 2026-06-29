package com.karigojobs.shared.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class SqlDeriverFactory actual constructor(context: Any?) {
    actual fun getSqlDriver(): SqlDriver {
        val driver = NativeSqliteDriver(
            KarigojobsDatabase.Schema,
            "KarigojobsDatabase.db"
        )

        driver.execute(
            identifier = null,
            sql = "PRAGMA foreign_keys = ON;",
            parameters = 0
        )

        return driver
    }
}