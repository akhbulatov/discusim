package com.akhbulatov.discusim.presentation.ui.forum.discussions

import com.akhbulatov.discusim.di.Flow3ChildFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumDiscussionsHostModule {
    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsModule::class])
    abstract fun contributeForumDiscussionsFragment(): ForumDiscussionsFragment
}