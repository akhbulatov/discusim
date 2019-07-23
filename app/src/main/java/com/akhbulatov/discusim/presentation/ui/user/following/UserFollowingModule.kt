package com.akhbulatov.discusim.presentation.ui.user.following

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserFollowingModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserFollowingViewModel::class)
    abstract fun bindUserFollowingViewModel(viewModel: UserFollowingViewModel): ViewModel
}
