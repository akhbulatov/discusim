package com.akhbulatov.discusim.presentation.ui.forum

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsModule
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ForumContainerModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumContainerViewModel::class)
    abstract fun bindForumContainerViewModel(viewModel: ForumContainerViewModel): ViewModel

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ForumDetailsModule::class])
    abstract fun contributeForumDetailsFragment(): ForumDetailsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ThreadsModule::class])
    abstract fun contributeThreadsFragment(): ThreadsFragment
}