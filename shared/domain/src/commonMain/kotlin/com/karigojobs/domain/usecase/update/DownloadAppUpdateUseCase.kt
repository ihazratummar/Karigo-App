package com.karigojobs.domain.usecase.update

import com.karigojobs.domain.repository.CustomUpdateRepository
import com.karigojobs.share.model.AppUpdateInfo
import com.karigojobs.share.model.AppUpdateState
import kotlinx.coroutines.flow.Flow

/**
 * UseCase to stream APK file download progress.
 */
class DownloadAppUpdateUseCase(
    private val repository: CustomUpdateRepository
) {
    operator fun invoke(updateInfo: AppUpdateInfo): Flow<AppUpdateState> {
        return repository.downloadApk(updateInfo.apkUrl, updateInfo)
    }
}
