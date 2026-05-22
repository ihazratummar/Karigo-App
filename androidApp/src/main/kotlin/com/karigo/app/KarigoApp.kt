package com.karigo.app

import android.app.Application
import android.content.Context
import com.karigo.di.initKoin
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 17/05/26
 */

class KarigoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@KarigoApp)
            modules(
                module {
                    single <String>{ applicationContext.filesDir.absolutePath }
                }
            )
        }
    }

}