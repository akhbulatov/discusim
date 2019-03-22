package com.akhbulatov.discusim.presentation.ui.forum

import androidx.lifecycle.ViewModel
import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.di.ViewModelKey
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import com.akhbulatov.discusim.presentation.ui.users.UsersFragment
import com.akhbulatov.discusim.presentation.ui.users.UsersModule
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
    @ContributesAndroidInjector(modules = [UsersModule::class])
    abstract fun contributeUsersFragment(): UsersFragment
}