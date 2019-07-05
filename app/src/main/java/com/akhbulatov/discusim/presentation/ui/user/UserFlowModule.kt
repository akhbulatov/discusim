package com.akhbulatov.discusim.presentation.ui.user

import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.user.activity.UserActivityFragment
import com.akhbulatov.discusim.presentation.ui.user.activity.UserActivityModule
import com.akhbulatov.discusim.presentation.ui.user.comments.UserCommentsFragment
import com.akhbulatov.discusim.presentation.ui.user.comments.UserCommentsModule
import com.akhbulatov.discusim.presentation.ui.user.details.UserDetailsFragment
import com.akhbulatov.discusim.presentation.ui.user.details.UserDetailsModule
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
}