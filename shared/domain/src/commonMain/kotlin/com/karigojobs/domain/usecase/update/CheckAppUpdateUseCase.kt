package com.karigojobs.domain.usecase.update

import com.karigojobs.domain.repository.AppVersionProvider
import com.karigojobs.domain.repository.CustomUpdateRepository
import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.AppUpdateState

/**
 * UseCase to check if a remote update is available.
 */
class CheckAppUpdateUseCase(
    private val repository: CustomUpdateRepository,
    private val appVersionProvider: AppVersionProvider
) {
    suspend operator fun invoke(overrideVersionCode: Long? = null): AppUpdateState {
        val currentVersionCode = overrideVersionCode ?: appVersionProvider.getAppVersion().versionCode
        return when (val result = repository.fetchUpdateInfo()) {
            is Result.Success -> {
                val remoteInfo = result.data
                if (remoteInfo.versionCode > currentVersionCode) {
                    AppUpdateState.UpdateAvailable(remoteInfo)
                } else {
                    AppUpdateState.UpToDate
                }
            }
            is Result.Error -> {
                AppUpdateState.Error(result.error)
            }
        }
    }
}
