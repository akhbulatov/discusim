package com.akhbulatov.discusim.di.modules

import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {
    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}