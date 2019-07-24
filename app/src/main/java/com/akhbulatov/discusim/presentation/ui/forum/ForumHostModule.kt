package com.akhbulatov.discusim.presentation.ui.forum

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsModule
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsModule
import com.akhbulatov.discusim.presentation.ui.forum.moderators.ForumModeratorsFragment
import com.akhbulatov.discusim.presentation.ui.forum.moderators.ForumModeratorsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumHostModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsModule::class])
    abstract fun contributeForumDetailsFragment(): ForumDetailsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDiscussionsModule::class])
    abstract fun contributeForumDiscussionsFragment(): ForumDiscussionsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumModeratorsModule::class])
    abstract fun contributeForumModeratorsFragment(): ForumModeratorsFragment
}
