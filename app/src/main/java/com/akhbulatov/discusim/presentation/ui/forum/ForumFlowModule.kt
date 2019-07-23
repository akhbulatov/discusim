package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumHostModule::class])
    abstract fun contributeForumHostFragment(): ForumHostFragment

    // TODO
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}
