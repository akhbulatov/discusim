package com.akhbulatov.discusim.di.modules

import android.content.Context
import android.content.res.AssetManager
import com.akhbulatov.discusim.App
import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.domain.global.AppSchedulers
import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.AndroidResourceManager
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import dagger.Binds
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Router
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
        fun provideContext(app: App): Context = app.applicationContext

        @JvmStatic
        @Provides
        fun provideAssetManager(context: Context): AssetManager = context.assets

        @JvmStatic
        @Provides
        @Singleton
        fun provideErrorHandler(
            router: Router,
            sessionInteractor: SessionInteractor,
            resourceManager: ResourceManager
        ) = ErrorHandler(router, sessionInteractor, resourceManager)

        @JvmStatic
        @Provides
        @Singleton
        fun provideAppInfo() = AppInfo(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}
