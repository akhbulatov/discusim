package com.akhbulatov.discusim.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.akhbulatov.discusim.data.global.local.prefs.PreferencesStorage
import com.akhbulatov.discusim.data.global.local.prefs.SharedPreferencesStorage
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class PreferencesModule {
    @Binds
    @Singleton
    abstract fun bindPreferencesStorage(storage: SharedPreferencesStorage): PreferencesStorage

    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideSharedPreferences(context: Context): SharedPreferences =
            context.getSharedPreferences("discusim.prefs", Context.MODE_PRIVATE)
    }
}