package com.unircorn.csp.app

import androidx.multidex.MultiDexApplication
import com.unircorn.csp.app.module.apiModule
import com.unircorn.csp.app.module.appModule
import com.unircorn.csp.app.module.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            val modules = modules(appModule, networkModule, apiModule)
        }
    }

}


