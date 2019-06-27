package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsContainerModule::class])
    abstract fun contributeForumDetailsContainerFragment(): ForumDetailsContainerFragment
}