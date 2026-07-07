package com.karigojobs.domain.usecase.backup

import com.karigojobs.domain.repository.GoogleDriveRepository
import com.karigojobs.domain.result.BackupError
import com.karigojobs.domain.result.Result

class GetLastBackupTimestampUseCase(
    private val repository: GoogleDriveRepository
) {
    suspend operator fun invoke(accessToken: String): Result<Long?, BackupError> {
        return repository.getLastBackupTimestamp(accessToken)
    }
}
