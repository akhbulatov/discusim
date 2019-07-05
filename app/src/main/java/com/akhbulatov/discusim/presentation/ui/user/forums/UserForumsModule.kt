package com.akhbulatov.discusim.presentation.ui.user.forums

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserForumsModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserForumsViewModel::class)
    abstract fun bindUserForumsViewModel(viewModel: UserForumsViewModel): ViewModel
}