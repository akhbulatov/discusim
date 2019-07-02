package com.akhbulatov.discusim.presentation.ui.main.my.activity

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.data.activity.ActivityRepositoryImpl
import com.akhbulatov.discusim.di.FlowChildFragmentScope
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.domain.global.eventbus.CursorStore
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
abstract class MyActivityModule {
    @Binds
    @IntoMap
    @ViewModelKey(MyActivityViewModel::class)
    abstract fun bindMyActivityViewModel(viewModel: MyActivityViewModel): ViewModel

    @Binds
    @FlowChildFragmentScope
    abstract fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository

    @Module
    companion object {
        @JvmStatic
        @Provides
        @FlowChildFragmentScope
        fun provideCursorStore() = CursorStore()
    }
}