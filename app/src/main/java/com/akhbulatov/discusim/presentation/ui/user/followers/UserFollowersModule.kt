package com.akhbulatov.discusim.presentation.ui.user.followers

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserFollowersModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserFollowersViewModel::class)
    abstract fun bindUserFollowersViewModel(viewModel: UserFollowersViewModel): ViewModel
}
