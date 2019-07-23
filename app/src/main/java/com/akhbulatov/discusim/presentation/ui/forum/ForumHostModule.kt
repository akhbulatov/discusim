package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsHostModule
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsHostModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumHostModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsHostModule::class])
    abstract fun contributeForumDetailsHostFragment(): ForumDetailsHostFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsHostModule::class])
    abstract fun contributeForumDiscussionsHostFragment(): ForumDiscussionsHostFragment
}
