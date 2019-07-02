package com.akhbulatov.discusim.presentation.ui.forum.threads

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumThreadsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumThreadsViewModel::class)
    abstract fun bindForumThreadsViewModel(viewModel: ForumThreadsViewModel): ViewModel
}