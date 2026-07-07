package com.karigojobs.feature.settings.backup

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.karigojobs.domain.repository.GoogleDriveRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BackupWorker(
    private val appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams), KoinComponent {

    private val googleDriveRepository: GoogleDriveRepository by inject()

    override suspend fun doWork(): Result {
        val account = GoogleDriveAuth.getSignedInAccount(appContext) ?: return Result.failure()

        val token = GoogleDriveAuth.getAccessToken(appContext, account) ?: return Result.retry()

        val result = googleDriveRepository.uploadBackup(token)
        return if (result is com.karigojobs.domain.result.Result.Success) {
            Result.success()
        } else {
            Result.retry()
        }
    }
}
