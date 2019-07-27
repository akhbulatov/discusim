package com.akhbulatov.discusim.di.modules

import android.content.Context
import android.content.res.AssetManager
import android.net.ConnectivityManager
import com.akhbulatov.discusim.App
import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.domain.global.AppSchedulers
import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppInfo
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
        fun provideContext(app: App): Context = app.applicationContext

        @JvmStatic
        @Provides
        fun provideAssetManager(context: Context): AssetManager = context.assets

        @JvmStatic
        @Provides
        fun provideConnectivityManager(context: Context): ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        @JvmStatic
        @Provides
        @Singleton
        fun provideAppInfo() = AppInfo(BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }
}
