package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forums.ForumsFragment
import com.akhbulatov.discusim.presentation.ui.forums.ForumsModule
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityFragment
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityModule
import com.akhbulatov.discusim.presentation.ui.profile.ProfileFragment
import com.akhbulatov.discusim.presentation.ui.profile.ProfileModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [MyActivityModule::class])
    abstract fun contributeMyActivityFragment(): MyActivityFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumsModule::class])
    abstract fun contributeForumsFragment(): ForumsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment
}