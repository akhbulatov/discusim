package com.akhbulatov.discusim.presentation.ui.auth

import com.akhbulatov.discusim.di.scopes.FlowChildFragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class AuthFlowModule {
    @FlowChildFragmentScope
    @ContributesAndroidInjector(modules = [AuthModule::class])
    abstract fun contributeAuthFragment(): AuthFragment
}