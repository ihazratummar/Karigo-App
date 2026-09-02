package com.karigojobs.domain.repository

import com.karigojobs.domain.result.Result
import com.karigojobs.share.model.AppUpdateInfo
import com.karigojobs.share.model.AppUpdateState
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for checking remote app updates and downloading APK files.
 */
interface CustomUpdateRepository {
    suspend fun fetchUpdateInfo(): Result<AppUpdateInfo, String>
    fun downloadApk(apkUrl: String, updateInfo: AppUpdateInfo): Flow<AppUpdateState>
}
