package com.akhbulatov.discusim.presentation.ui.auth

import com.akhbulatov.discusim.di.scopes.FlowChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.profile.comments.ProfileCommentsFragment
import com.akhbulatov.discusim.presentation.ui.profile.comments.ProfileCommentsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun contributeAuthFragment(): AuthFragment

    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [ProfileCommentsModule::class])
    abstract fun contributeProfileCommentsFragment(): ProfileCommentsFragment
}