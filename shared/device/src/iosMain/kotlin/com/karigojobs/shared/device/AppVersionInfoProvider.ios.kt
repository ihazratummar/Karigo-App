package com.karigojobs.shared.device

import platform.Foundation.NSBundle

actual class AppVersionInfoProvider {
    actual fun getAppVersion(): AppVersionData {
        val versionName = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "1.0.0"
        val versionCode = (NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion") as? String)?.toLongOrNull() ?: 32L
        return AppVersionData(versionName = versionName, versionCode = versionCode)
    }
}
