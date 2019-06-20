package com.akhbulatov.discusim.presentation.ui.profile.activity

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileActivityViewModel::class)
    abstract fun bindProfileActivitiesViewModel(viewModel: ProfileActivityViewModel): ViewModel
}