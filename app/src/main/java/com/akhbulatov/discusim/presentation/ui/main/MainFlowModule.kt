package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityFragment
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityModule
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsFragment
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [MyActivityModule::class])
    abstract fun contributeMyActivityFragment(): MyActivityFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [MyForumsModule::class])
    abstract fun contributeMyForumsFragment(): MyForumsFragment
}