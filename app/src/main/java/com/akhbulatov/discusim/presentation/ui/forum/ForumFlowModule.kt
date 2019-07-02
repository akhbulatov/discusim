package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerModule
import com.akhbulatov.discusim.presentation.ui.forum.threads.ForumThreadsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.threads.ForumThreadsContainerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsContainerModule::class])
    abstract fun contributeForumDetailsContainerFragment(): ForumDetailsContainerFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumThreadsContainerModule::class])
    abstract fun contributeForumThreadsContainerFragment(): ForumThreadsContainerFragment
}