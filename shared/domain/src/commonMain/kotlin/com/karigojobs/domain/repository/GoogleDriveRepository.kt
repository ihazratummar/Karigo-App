package com.karigojobs.domain.repository

import com.karigojobs.domain.result.Result
import com.karigojobs.domain.result.BackupError

interface GoogleDriveRepository {
    /**
     * Uploads the backup database to Google Drive appDataFolder.
     * @param accessToken The OAuth access token with drive.appdata scope.
     */
    suspend fun uploadBackup(accessToken: String): Result<Unit, BackupError>

    /**
     * Downloads the latest backup database from Google Drive.
     * @param accessToken The OAuth access token with drive.appdata scope.
     */
    suspend fun downloadLatestBackup(accessToken: String): Result<Unit, BackupError>

    /**
     * Checks if a backup exists and returns the timestamp of the last backup, or null if none.
     */
    suspend fun getLastBackupTimestamp(accessToken: String): Result<Long?, BackupError>
}
