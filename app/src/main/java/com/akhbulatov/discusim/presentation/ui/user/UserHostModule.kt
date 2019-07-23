package com.akhbulatov.discusim.presentation.ui.user

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
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
abstract class UserHostModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserDetailsModule::class])
    abstract fun contributeUserDetailsFragment(): UserDetailsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserActivityModule::class])
    abstract fun contributeUserActivityFragment(): UserActivityFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserCommentsModule::class])
    abstract fun contributeUserCommentsFragment(): UserCommentsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserForumsModule::class])
    abstract fun contributeUserForumsFragment(): UserForumsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserFollowersModule::class])
    abstract fun contributeUserFollowersFragment(): UserFollowersFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UserFollowingModule::class])
    abstract fun contributeUserFollowingFragment(): UserFollowingFragment
}
