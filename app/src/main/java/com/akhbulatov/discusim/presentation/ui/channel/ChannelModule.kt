package com.akhbulatov.discusim.presentation.ui.channel

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.di.scopes.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ChannelModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelViewModel::class)
    abstract fun bindChannelViewModel(viewModel: ChannelViewModel): ViewModel

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ThreadsModule::class])
    abstract fun contributeThreadsFragment(): ThreadsFragment
}