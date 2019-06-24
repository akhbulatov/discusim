package com.akhbulatov.discusim.presentation.ui.trends

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class TrendsContainerModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [ThreadsModule::class])
    abstract fun contributeThreadsFragment(): ThreadsFragment
}