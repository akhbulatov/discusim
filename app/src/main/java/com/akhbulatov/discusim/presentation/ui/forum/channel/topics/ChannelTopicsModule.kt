package com.akhbulatov.discusim.presentation.ui.forum.channel.topics

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChannelTopicsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelTopicsViewModel::class)
    abstract fun bindChannelTopicsViewModel(viewModel: ChannelTopicsViewModel): ViewModel
}
