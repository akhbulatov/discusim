package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forums.ForumsFragment
import com.akhbulatov.discusim.presentation.ui.forums.ForumsModule
import com.akhbulatov.discusim.presentation.ui.profile.ProfileFragment
import com.akhbulatov.discusim.presentation.ui.profile.ProfileModule
import com.akhbulatov.discusim.presentation.ui.profile.activity.UserActivityFragment
import com.akhbulatov.discusim.presentation.ui.profile.activity.UserActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserActivityModule::class])
    abstract fun contributeUserActivityFragment(): UserActivityFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumsModule::class])
    abstract fun contributeForumsFragment(): ForumsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment
}