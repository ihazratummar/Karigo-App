package com.karigojobs.app.android.services.di

import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.karigojobs.app.android.services.UpdateManager
import com.karigojobs.app.android.services.UpdateManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 05/06/26
 */



fun getUpdateModule() : Module = module {
    single {
        AppUpdateManagerFactory.create(androidContext())
    }

    single<UpdateManager> {
        UpdateManagerImpl(
            context = androidContext(),
            appUpdateManager = get(),
            updateType = AppUpdateType.IMMEDIATE
        )
    }

    single { com.karigojobs.app.android.services.ApkInstaller(androidContext()) }
}

