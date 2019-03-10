package com.akhbulatov.discusim.presentation.ui.profile.following

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileFollowingModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileFollowingViewModel::class)
    abstract fun bindProfileFollowingViewModel(viewModel: ProfileFollowingViewModel): ViewModel
}