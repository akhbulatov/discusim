package com.akhbulatov.discusim.di.modules

import dagger.Module
import dagger.Provides
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.navigationfactories.GeneratedNavigationFactory
import me.aartikov.alligator.navigationfactories.NavigationFactory
import javax.inject.Singleton

@Module
object NavigationModule {
    private val androidNavigator = AndroidNavigator(GeneratedNavigationFactory())

    @JvmStatic
    @Provides
    @Singleton
    fun provideNavigationFactory(): NavigationFactory = androidNavigator.navigationFactory

    @JvmStatic
    @Provides
    @Singleton
    fun provideNavigator(): Navigator = androidNavigator

    @JvmStatic
    @Provides
    @Singleton
    fun provideNavigationContextBinder(): NavigationContextBinder = androidNavigator

    @JvmStatic
    @Provides
    @Singleton
    fun provideScreenResolver(): ScreenResolver = androidNavigator.screenResolver
}