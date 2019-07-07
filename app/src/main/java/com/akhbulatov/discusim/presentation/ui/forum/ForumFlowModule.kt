package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerModule
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsContainerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsContainerModule::class])
    abstract fun contributeForumDetailsContainerFragment(): ForumDetailsContainerFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsContainerModule::class])
    abstract fun contributeForumDiscussionsContainerFragment(): ForumDiscussionsContainerFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}