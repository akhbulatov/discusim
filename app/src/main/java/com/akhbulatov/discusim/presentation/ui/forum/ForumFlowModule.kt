package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import com.akhbulatov.discusim.presentation.ui.forum.channel.ChannelHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.channel.ChannelHostModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumHostModule::class])
    abstract fun contributeForumHostFragment(): ForumHostFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ChannelHostModule::class])
    abstract fun contributeChannelHostFragment(): ChannelHostFragment

    // TODO
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}
