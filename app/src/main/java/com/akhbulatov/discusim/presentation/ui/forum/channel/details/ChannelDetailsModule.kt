package com.akhbulatov.discusim.presentation.ui.forum.channel.details

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ChannelDetailsModule {
    @Binds
    @IntoMap
    @ViewModelKey(ChannelDetailsViewModel::class)
    abstract fun bindChannelDetailsViewModel(viewModel: ChannelDetailsViewModel): ViewModel
}
