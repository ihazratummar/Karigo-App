package com.karigojobs.domain.repository

interface BackupFileHandler {
    /**
     * Extracts the local database into a ByteArray for backup.
     */
    suspend fun getBackupBytes(): ByteArray

    /**
     * Suspends the local driver, overwrites the database file with the downloaded bytes,
     * and restarts the driver for a hot-reload.
     */
    suspend fun restoreBackup(bytes: ByteArray)
}
