package com.akhbulatov.discusim.presentation.ui.user

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserHostModule::class])
    abstract fun contributeUserHostFragment(): UserHostFragment

    // TODO
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}