package com.karigojobs.domain.repository

import com.karigojobs.share.model.AppVersionData

interface AppVersionProvider {
    fun getAppVersion(): AppVersionData
}
