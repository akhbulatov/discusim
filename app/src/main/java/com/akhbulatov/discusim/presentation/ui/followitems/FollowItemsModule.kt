package com.akhbulatov.discusim.presentation.ui.followitems

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FollowItemsModule {
    @Binds
    @IntoMap
    @ViewModelKey(FollowItemsViewModel::class)
    abstract fun bindFollowItemsViewModel(viewModel: FollowItemsViewModel): ViewModel
}