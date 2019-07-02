package com.akhbulatov.discusim.presentation.ui.forum.threads

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumThreadsContainerModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumThreadsModule::class])
    abstract fun contributeForumThreadsFragment(): ForumThreadsFragment
}