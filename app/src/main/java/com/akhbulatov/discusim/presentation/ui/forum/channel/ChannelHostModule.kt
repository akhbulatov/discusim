package com.akhbulatov.discusim.presentation.ui.forum.channel

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.channel.details.ChannelDetailsHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.channel.details.ChannelDetailsHostModule
import com.akhbulatov.discusim.presentation.ui.forum.channel.discussions.ChannelDiscussionsHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.channel.discussions.ChannelDiscussionsHostModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChannelHostModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ChannelDetailsHostModule::class])
    abstract fun contributeChannelDetailsHostFragment(): ChannelDetailsHostFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ChannelDiscussionsHostModule::class])
    abstract fun contributeChannelDiscussionsHostFragment(): ChannelDiscussionsHostFragment
}
