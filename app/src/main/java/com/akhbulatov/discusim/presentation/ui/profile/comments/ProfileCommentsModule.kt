package com.akhbulatov.discusim.presentation.ui.profile.comments

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileCommentsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileCommentsViewModel::class)
    abstract fun bindProfileCommentsViewModel(viewModel: ProfileCommentsViewModel): ViewModel
}