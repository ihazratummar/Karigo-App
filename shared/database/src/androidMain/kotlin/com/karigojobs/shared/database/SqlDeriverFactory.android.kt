package com.karigojobs.shared.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class SqlDeriverFactory actual constructor(context: Any?) {

    private val context = context as Context

    actual fun getSqlDriver(): SqlDriver {
        val driver = AndroidSqliteDriver(
            schema = KarigojobsDatabase.Schema,
            context = context,
            name = "KarigojobsDatabase.db"
        )
        driver.execute(
            identifier = null,
            sql = "PRAGMA foreign_keys = ON;",
            parameters = 0
        )

        return driver
    }
}