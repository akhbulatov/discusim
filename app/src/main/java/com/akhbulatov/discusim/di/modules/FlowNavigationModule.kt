package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.di.Flow
import com.akhbulatov.discusim.di.FlowFragmentScope
import com.akhbulatov.discusim.presentation.global.FlowRouter
import dagger.Module
import dagger.Provides
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router

@Module
object FlowNavigationModule {
    @JvmStatic
    @Provides
    @FlowFragmentScope
    fun provideFlowCicerone(appRouter: Router): Cicerone<FlowRouter> = Cicerone.create(FlowRouter(appRouter))

    @JvmStatic
    @Provides
    @FlowFragmentScope
    fun provideFlowRouter(cicerone: Cicerone<FlowRouter>): FlowRouter = cicerone.router

    @JvmStatic
    @Provides
    @FlowFragmentScope
    @Flow
    fun provideFlowNavigatorHolder(cicerone: Cicerone<FlowRouter>): NavigatorHolder = cicerone.navigatorHolder
}
