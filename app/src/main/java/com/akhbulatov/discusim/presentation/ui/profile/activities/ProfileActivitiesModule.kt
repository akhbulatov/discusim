package com.akhbulatov.discusim.presentation.ui.profile.activities

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileActivitiesModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileActivitiesViewModel::class)
    abstract fun bindProfileActivitiesViewModel(viewModel: ProfileActivitiesViewModel): ViewModel
}