package com.karigojobs.data.repository

expect class AppPathProvider {
    fun getDatabasePath(): String
    fun getDatastorePath(fileName: String): String
    fun restartApp()
    fun extractLegacyBackup(bytes: ByteArray): Boolean
}
