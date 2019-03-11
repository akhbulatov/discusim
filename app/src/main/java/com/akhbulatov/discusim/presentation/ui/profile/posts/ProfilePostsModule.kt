package com.akhbulatov.discusim.presentation.ui.profile.posts

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfilePostsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfilePostsViewModel::class)
    abstract fun bindProfilePostsViewModel(viewModel: ProfilePostsViewModel): ViewModel
}