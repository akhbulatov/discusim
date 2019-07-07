package com.akhbulatov.discusim.presentation.ui.forum.discussions

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumDiscussionsContainerModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsModule::class])
    abstract fun contributeForumDiscussionsFragment(): ForumDiscussionsFragment
}