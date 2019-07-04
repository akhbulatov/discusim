package com.akhbulatov.discusim.presentation.ui.forum.details.topics

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumTopicsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumTopicsViewModel::class)
    abstract fun bindForumTopicsViewModel(viewModel: ForumTopicsViewModel): ViewModel
}