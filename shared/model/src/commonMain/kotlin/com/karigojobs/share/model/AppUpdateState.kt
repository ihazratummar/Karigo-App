package com.karigojobs.share.model

/**
 * Lifecycle state for custom app update detection and installation.
 */
sealed interface AppUpdateState {
    object Idle : AppUpdateState
    object Checking : AppUpdateState
    data class UpdateAvailable(val info: AppUpdateInfo) : AppUpdateState
    data class Downloading(
        val info: AppUpdateInfo,
        val progressPercent: Int,
        val downloadedMB: String
    ) : AppUpdateState
    data class ReadyToInstall(
        val info: AppUpdateInfo,
        val apkFilePath: String
    ) : AppUpdateState
    object UpToDate : AppUpdateState
    data class Error(val message: String) : AppUpdateState
}
