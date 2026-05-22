package com.karigo.app

import android.app.Application
import android.content.Context
import com.karigo.di.initKoin
import org.koin.dsl.module


/**
 * @author hazratummar
 * Created on 17/05/26
 */

class KarigoApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            modules(
                module {
                    single <Context>{ this@KarigoApp.applicationContext }
                    single <String>{ applicationContext.filesDir.absolutePath }
                }
            )
        }
    }

}