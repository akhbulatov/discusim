package com.akhbulatov.discusim.presentation.ui.user.activity

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserActivityViewModel::class)
    abstract fun bindUserActivityViewModel(viewModel: UserActivityViewModel): ViewModel
}