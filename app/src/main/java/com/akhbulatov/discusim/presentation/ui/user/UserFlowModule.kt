package com.akhbulatov.discusim.presentation.ui.user

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsModule
import com.akhbulatov.discusim.presentation.ui.user.activity.UserActivityFragment
import com.akhbulatov.discusim.presentation.ui.user.activity.UserActivityModule
import com.akhbulatov.discusim.presentation.ui.user.comments.UserCommentsFragment
import com.akhbulatov.discusim.presentation.ui.user.comments.UserCommentsModule
import com.akhbulatov.discusim.presentation.ui.user.details.UserDetailsFragment
import com.akhbulatov.discusim.presentation.ui.user.details.UserDetailsModule
import com.akhbulatov.discusim.presentation.ui.user.followers.UserFollowersFragment
import com.akhbulatov.discusim.presentation.ui.user.followers.UserFollowersModule
import com.akhbulatov.discusim.presentation.ui.user.following.UserFollowingFragment
import com.akhbulatov.discusim.presentation.ui.user.following.UserFollowingModule
import com.akhbulatov.discusim.presentation.ui.user.forums.UserForumsFragment
import com.akhbulatov.discusim.presentation.ui.user.forums.UserForumsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UserFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserDetailsModule::class])
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserActivityModule::class])
    abstract fun contributeUserActivityFragment(): UserActivityFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserCommentsModule::class])
    abstract fun contributeUserCommentsFragment(): UserCommentsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserForumsModule::class])
    abstract fun contributeUserForumsFragment(): UserForumsFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserFollowersModule::class])
    abstract fun contributeUserFollowersFragment(): UserFollowersFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [UserFollowingModule::class])
    abstract fun contributeUserFollowingFragment(): UserFollowingFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [DiscussionDetailsModule::class])
    abstract fun contributeDiscussionDetailsFragment(): DiscussionDetailsFragment
}