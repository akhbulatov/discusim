package com.akhbulatov.discusim.presentation.ui.forum.details.moderators

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumModeratorsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumModeratorsViewModel::class)
    abstract fun bindForumModeratorsViewModel(viewModel: ForumModeratorsViewModel): ViewModel
}