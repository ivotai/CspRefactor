package com.unircorn.csp.app

import androidx.multidex.MultiDexApplication
import com.mikepenz.iconics.Iconics
import com.unicorn.sanre.icon.Fad
import com.unicorn.sanre.icon.Fal
import com.unicorn.sanre.icon.Far
import com.unicorn.sanre.icon.Fas
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
            modules(appModule, networkModule, apiModule)
        }

        initIconics()
    }

    private fun initIconics() {
        Iconics.init(applicationContext)
        Iconics.registerFont(Fal)
        Iconics.registerFont(Far)
        Iconics.registerFont(Fas)
        Iconics.registerFont(Fad)
    }

}


