package com.akhbulatov.discusim.presentation.ui.forum.discussions

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumDiscussionsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumDiscussionsViewModel::class)
    abstract fun bindForumDiscussionsViewModel(viewModel: ForumDiscussionsViewModel): ViewModel
}
