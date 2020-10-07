package com.sakb.spl.app

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import com.sakb.spl.BuildConfig
import com.sakb.spl.data.local.PrefManager
import com.sakb.spl.di.applicationModule
import com.sakb.spl.di.networkModule
import com.sakb.spl.di.repositoryModule
import com.sakb.spl.di.viewModelModule
import com.sakb.spl.utils.LocaleManager
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SplApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        PrefManager.initialize(this)
        LocaleManager.setLocale(this)
        startKoin {
            // Koin logger
            androidLogger()
            // inject Android context
            androidContext(this@SplApplication)
            modules(
                listOf(
                    applicationModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
        setupTimber()
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(LocaleManager.setLocale(base))
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        LocaleManager.setLocale(this)
    }


    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}