package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.global.local.prefs.PreferencesStorage
import com.akhbulatov.discusim.data.global.local.prefs.SharedPreferencesStorage
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class PrefsModule {
    @Binds
    @Singleton
    abstract fun bindPreferencesStorage(storage: SharedPreferencesStorage): PreferencesStorage
}