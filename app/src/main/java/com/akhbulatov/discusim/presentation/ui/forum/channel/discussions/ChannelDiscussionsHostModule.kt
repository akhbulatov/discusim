package com.akhbulatov.discusim.presentation.ui.forum.channel.discussions

import com.akhbulatov.discusim.di.Flow3ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ChannelDiscussionsHostModule {
    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsModule::class])
    abstract fun contributeForumDiscussionsFragment(): ForumDiscussionsFragment
}
