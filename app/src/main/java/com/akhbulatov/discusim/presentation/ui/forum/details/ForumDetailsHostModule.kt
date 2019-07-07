package com.akhbulatov.discusim.presentation.ui.forum.details

import com.akhbulatov.discusim.di.Flow3ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.topics.ForumTopicsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.topics.ForumTopicsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumDetailsHostModule {
    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsModule::class])
    abstract fun contributeForumDetailsFragment(): ForumDetailsFragment

    @Flow3ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumTopicsModule::class])
    abstract fun contributeForumTopicsFragment(): ForumTopicsFragment
}