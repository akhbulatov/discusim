package com.akhbulatov.discusim.di.modules

import dagger.Module
import dagger.Provides
import javax.inject.Singleton
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
object NavigationModule {
    private val cicerone: Cicerone<Router> = Cicerone.create()

    @JvmStatic
    @Provides
    @Singleton
    fun provideRouter(): Router = cicerone.router

    @JvmStatic
    @Provides
    @Singleton
    fun provideNavigatorHolder(): NavigatorHolder = cicerone.navigatorHolder
}
