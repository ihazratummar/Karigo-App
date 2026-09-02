package com.karigojobs.share.model

/**
 * Data model representing remote app update metadata.
 */
data class AppUpdateInfo(
    val versionCode: Int,
    val versionName: String,
    val fileSizeBytes: Long = 0L,
    val fileSizeFormatted: String = "",
    val whatsNew: List<String> = emptyList(),
    val apkUrl: String = "",
    val playStoreUrl: String = "",
    val isForceUpdate: Boolean = false
)
