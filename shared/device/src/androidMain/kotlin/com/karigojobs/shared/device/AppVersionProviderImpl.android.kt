package com.karigojobs.shared.device

import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import com.karigojobs.domain.repository.AppVersionProvider
import com.karigojobs.share.model.AppVersionData

class AppVersionProviderImpl(private val context: Context) : AppVersionProvider {
    override fun getAppVersion(): AppVersionData {
        return try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            val versionName = packageInfo.versionName ?: "1.0.0"
            val versionCode = PackageInfoCompat.getLongVersionCode(packageInfo)
            AppVersionData(versionName = versionName, versionCode = versionCode)
        } catch (e: Exception) {
            AppVersionData(versionName = "1.0.0", versionCode = 32L)
        }
    }
}
