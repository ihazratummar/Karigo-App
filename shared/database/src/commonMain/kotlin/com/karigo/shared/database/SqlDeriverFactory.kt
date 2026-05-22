package com.karigo.shared.database

import app.cash.sqldelight.db.SqlDriver


/**
 * @author hazratummar
 * Created on 22/05/26
 */

expect class SqlDeriverFactory(context: Any? = null){
    fun getSqlDriver() : SqlDriver
}