package com.akhbulatov.discusim.presentation.ui.profile.followers

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileFollowersModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileFollowersViewModel::class)
    abstract fun bindProfileFollowersViewModel(viewModel: ProfileFollowersViewModel): ViewModel
}