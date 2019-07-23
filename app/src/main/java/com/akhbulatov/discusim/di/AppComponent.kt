package com.akhbulatov.discusim.di

import com.akhbulatov.discusim.App
import com.akhbulatov.discusim.di.modules.ActivityBindingModule
import com.akhbulatov.discusim.di.modules.AppModule
import com.akhbulatov.discusim.di.modules.DataModule
import com.akhbulatov.discusim.di.modules.NavigationModule
import com.akhbulatov.discusim.di.modules.NetworkModule
import com.akhbulatov.discusim.di.modules.PreferencesModule
import com.akhbulatov.discusim.di.modules.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        PreferencesModule::class,
        NavigationModule::class,
        DataModule::class,
        ViewModelModule::class,
        ActivityBindingModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<App>
}
