package com.akhbulatov.discusim.presentation.ui.main.my.profile

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MyProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyProfileViewModel::class)
    abstract fun bindMyProfileViewModel(viewModel: MyProfileViewModel): ViewModel
}
