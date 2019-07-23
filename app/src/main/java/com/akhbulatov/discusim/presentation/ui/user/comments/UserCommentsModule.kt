package com.akhbulatov.discusim.presentation.ui.user.comments

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class UserCommentsModule {
    @Binds
    @IntoMap
    @ViewModelKey(UserCommentsViewModel::class)
    abstract fun bindUserCommentsViewModel(viewModel: UserCommentsViewModel): ViewModel
}
