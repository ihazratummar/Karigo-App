package com.karigo.shared.database

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver

actual class SqlDeriverFactory actual constructor(context: Any?) {
    actual fun getSqlDriver(): SqlDriver {
        return NativeSqliteDriver(
            KarigoDatabase.Schema,
            "KarigoDatabase.db"
        )
    }
}