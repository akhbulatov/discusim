package com.akhbulatov.discusim.presentation.ui.thread.details

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ThreadDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ThreadDetailsViewModel::class)
    abstract fun bindThreadDetailsViewModel(viewModel: ThreadDetailsViewModel): ViewModel
}