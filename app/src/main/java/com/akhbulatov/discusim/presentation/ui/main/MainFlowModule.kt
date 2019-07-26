package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.about.AboutFragment
import com.akhbulatov.discusim.presentation.ui.about.AboutModule
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [MainHostModule::class])
    abstract fun contributeMainHostFragment(): MainHostFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [AboutModule::class])
    abstract fun contributeAboutFragment(): AboutFragment

    // TODO
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}
