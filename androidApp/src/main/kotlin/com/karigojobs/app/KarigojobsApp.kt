package com.karigojobs.app

import android.app.Application
import android.content.Context
import com.karigojobs.app.android.services.di.getUpdateModule
import com.karigojobs.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 17/05/26
 */

class KarigojobsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        com.karigojobs.data.billing.ActivityProvider.init(this)

        initKoin {
            androidContext(this@KarigojobsApp)
            modules(
                module {
                    single<String> { applicationContext.filesDir.absolutePath }
                },
                getUpdateModule()
            )
        }
    }

}