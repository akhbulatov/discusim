package com.akhbulatov.discusim.presentation.ui

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.FlowFragmentScope
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.di.modules.FlowNavigationModule
import com.akhbulatov.discusim.presentation.ui.auth.AuthFlowFragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFlowModule
import com.akhbulatov.discusim.presentation.ui.forum.ForumFlowFragment
import com.akhbulatov.discusim.presentation.ui.forum.ForumFlowModule
import com.akhbulatov.discusim.presentation.ui.main.MainFlowFragment
import com.akhbulatov.discusim.presentation.ui.main.MainFlowModule
import com.akhbulatov.discusim.presentation.ui.user.UserFlowFragment
import com.akhbulatov.discusim.presentation.ui.user.UserFlowModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class AppActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(AppActivityViewModel::class)
    abstract fun bindAppActivityViewModel(viewModel: AppActivityViewModel): ViewModel

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [AuthFlowModule::class, FlowNavigationModule::class])
    abstract fun contributeAuthFlowFragment(): AuthFlowFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [MainFlowModule::class, FlowNavigationModule::class])
    abstract fun contributeMainFlowFragment(): MainFlowFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [ForumFlowModule::class, FlowNavigationModule::class])
    abstract fun contributeForumFlowFragment(): ForumFlowFragment

    @FlowFragmentScope
    @ContributesAndroidInjector(modules = [UserFlowModule::class, FlowNavigationModule::class])
    abstract fun contributeUserFlowFragment(): UserFlowFragment
}