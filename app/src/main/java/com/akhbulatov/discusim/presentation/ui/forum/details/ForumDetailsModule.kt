package com.akhbulatov.discusim.presentation.ui.forum.details

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumDetailsViewModel::class)
    abstract fun bindForumDetailsViewModel(viewModel: ForumDetailsViewModel): ViewModel
}