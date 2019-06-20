package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forums.ForumsFragment
import com.akhbulatov.discusim.presentation.ui.forums.ForumsModule
import com.akhbulatov.discusim.presentation.ui.profile.ProfileFragment
import com.akhbulatov.discusim.presentation.ui.profile.ProfileModule
import com.akhbulatov.discusim.presentation.ui.profile.activity.ProfileActivityFragment
import com.akhbulatov.discusim.presentation.ui.profile.activity.ProfileActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileActivityModule::class])
    abstract fun contributeProfileActivityFragment(): ProfileActivityFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumsModule::class])
    abstract fun contributeForumsFragment(): ForumsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileModule::class])
    abstract fun contributeProfileFragment(): ProfileFragment
}