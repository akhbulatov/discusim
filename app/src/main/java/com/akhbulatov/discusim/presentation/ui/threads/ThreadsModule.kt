package com.akhbulatov.discusim.presentation.ui.threads

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ThreadsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ThreadsViewModel::class)
    abstract fun bindThreadsViewModel(viewModel: ThreadsViewModel): ViewModel
}