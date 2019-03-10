package com.akhbulatov.discusim.presentation.ui.profile

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel
}