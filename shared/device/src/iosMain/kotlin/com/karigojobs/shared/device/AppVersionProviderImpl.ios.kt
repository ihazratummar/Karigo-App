package com.karigojobs.shared.device

import com.karigojobs.domain.repository.AppVersionProvider
import com.karigojobs.share.model.AppVersionData
import platform.Foundation.NSBundle

class AppVersionProviderImpl : AppVersionProvider {
    override fun getAppVersion(): AppVersionData {
        val versionName = NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleShortVersionString") as? String ?: "1.0.0"
        val versionCode = (NSBundle.mainBundle.objectForInfoDictionaryKey("CFBundleVersion") as? String)?.toLongOrNull() ?: 32L
        return AppVersionData(versionName = versionName, versionCode = versionCode)
    }
}
