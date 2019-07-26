package com.akhbulatov.discusim.presentation.ui.about

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AboutModule {
    @Binds
    @IntoMap
    @ViewModelKey(AboutViewModel::class)
    abstract fun bindAboutViewModel(viewModel: AboutViewModel): ViewModel
}
