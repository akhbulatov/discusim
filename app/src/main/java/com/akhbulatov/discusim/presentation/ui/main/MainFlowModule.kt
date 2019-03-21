package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.scopes.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.profile.ProfileFragment
import com.akhbulatov.discusim.presentation.ui.profile.ProfileModule
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ThreadsModule::class])
    abstract fun contributeThreadsFragment(): ThreadsFragment
}