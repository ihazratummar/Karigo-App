package com.karigojobs.shared.device

data class AppVersionData(
    val versionName: String,
    val versionCode: Long
)

expect class AppVersionInfoProvider {
    fun getAppVersion(): AppVersionData
}
