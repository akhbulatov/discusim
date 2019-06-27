package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumContainerModule::class])
    abstract fun contributeForumContainerFragment(): ForumContainerFragment
}