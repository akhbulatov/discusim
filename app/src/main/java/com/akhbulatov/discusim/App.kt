package com.akhbulatov.discusim

import com.akhbulatov.discusim.di.DaggerAppComponent
import com.facebook.stetho.Stetho
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import timber.log.Timber

class App : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        initThreeTenAbp()
        initTimber()
        initStetho()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

    private fun initThreeTenAbp() {
        AndroidThreeTen.init(this)
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}
