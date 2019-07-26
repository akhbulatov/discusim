package com.akhbulatov.discusim.presentation.ui.about.libraries

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AppLibrariesModule {
    @Binds
    @IntoMap
    @ViewModelKey(AppLibrariesViewModel::class)
    abstract fun bindAppLibrariesViewModel(viewModel: AppLibrariesViewModel): ViewModel
}
