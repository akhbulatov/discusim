package com.akhbulatov.discusim.presentation.ui.discussion.details

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DiscussionDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(DiscussionDetailsViewModel::class)
    abstract fun bindDiscussionDetailsViewModel(viewModel: DiscussionDetailsViewModel): ViewModel
}
