package com.karigo.shared.database

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver

actual class SqlDeriverFactory actual constructor(context: Any?) {

    private val context = context as Context

    actual fun getSqlDriver(): SqlDriver {
        return AndroidSqliteDriver(
            schema = KarigoDatabase.Schema,
            context = context,
            name = "KarigoDatabase.db"
        )
    }
}