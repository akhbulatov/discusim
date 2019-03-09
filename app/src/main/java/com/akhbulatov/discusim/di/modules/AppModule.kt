package com.akhbulatov.discusim.di.modules

import android.content.Context
import com.akhbulatov.discusim.App
import com.akhbulatov.discusim.domain.global.AppSchedulers
import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.presentation.global.AndroidResourceManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun bindSchedulersProvider(schedulers: AppSchedulers): SchedulersProvider

    @Binds
    @Singleton
    abstract fun bindResourceManager(resourceManager: AndroidResourceManager): ResourceManager

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideContext(app: App): Context = app.applicationContext
    }
}