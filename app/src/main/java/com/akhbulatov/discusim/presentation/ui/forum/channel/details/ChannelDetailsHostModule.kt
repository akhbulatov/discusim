package com.akhbulatov.discusim.presentation.ui.forum.channel.details

import com.akhbulatov.discusim.di.Flow3ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.channel.topics.ChannelTopicsFragment
import com.akhbulatov.discusim.presentation.ui.forum.channel.topics.ChannelTopicsModule
import com.akhbulatov.discusim.presentation.ui.forum.moderators.ForumModeratorsFragment
import com.akhbulatov.discusim.presentation.ui.forum.moderators.ForumModeratorsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChannelDetailsHostModule {
    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ChannelDetailsModule::class])
    abstract fun contributeChannelDetailsFragment(): ChannelDetailsFragment

    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ChannelTopicsModule::class])
    abstract fun contributeChannelTopicsFragment(): ChannelTopicsFragment

    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumModeratorsModule::class])
    abstract fun contributeForumModeratorsFragment(): ForumModeratorsFragment
}
