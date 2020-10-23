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
import com.zeugmasolutions.localehelper.LocaleAwareApplication
import com.zeugmasolutions.localehelper.LocaleHelperApplicationDelegate
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class SplApplication : LocaleAwareApplication() {

    override fun onCreate() {
        super.onCreate()
        PrefManager.initialize(this)
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

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}