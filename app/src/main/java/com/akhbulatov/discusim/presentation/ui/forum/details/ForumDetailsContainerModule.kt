package com.akhbulatov.discusim.presentation.ui.forum.details

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.topics.ForumTopicsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.topics.ForumTopicsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumDetailsContainerModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsModule::class])
    abstract fun contributeForumDetailsFragment(): ForumDetailsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumTopicsModule::class])
    abstract fun contributeForumTopicsFragment(): ForumTopicsFragment
}