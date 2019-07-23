package com.akhbulatov.discusim.presentation.ui.main.my.forums

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyForumsModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyForumsViewModel::class)
    abstract fun bindMyForumsViewModel(viewModel: MyForumsViewModel): ViewModel
}
