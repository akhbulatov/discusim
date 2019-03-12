package com.akhbulatov.discusim.presentation.ui.forum

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.di.scopes.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.followitems.FollowItemsFragment
import com.akhbulatov.discusim.presentation.ui.followitems.FollowItemsModule
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ForumModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForumViewModel::class)
    abstract fun bindForumViewModel(viewModel: ForumViewModel): ViewModel

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ThreadsModule::class])
    abstract fun contributeThreadsFragment(): ThreadsFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [FollowItemsModule::class])
    abstract fun contributeFollowItemsFragment(): FollowItemsFragment
}