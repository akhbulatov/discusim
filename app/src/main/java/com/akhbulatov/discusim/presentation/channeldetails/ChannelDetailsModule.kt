package com.akhbulatov.discusim.presentation.channeldetails

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.presentation.ui.users.UsersFragment
import com.akhbulatov.discusim.presentation.ui.users.UsersModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ChannelDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelDetailsViewModel::class)
    abstract fun bindChannelDetailsViewModel(viewModel: ChannelDetailsViewModel): ViewModel

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [UsersModule::class])
    abstract fun contributeUsersFragment(): UsersFragment
}