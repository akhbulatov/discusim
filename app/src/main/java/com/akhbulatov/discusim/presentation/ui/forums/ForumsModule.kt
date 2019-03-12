package com.akhbulatov.discusim.presentation.ui.forums

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForumsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumsViewModel::class)
    abstract fun bindForumsViewModel(viewModel: ForumsViewModel): ViewModel
}