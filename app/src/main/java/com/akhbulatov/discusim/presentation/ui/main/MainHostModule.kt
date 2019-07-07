package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.di.Flow2ChildFragmentScope
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityFragment
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityModule
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsFragment
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainHostModule {
    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [MyActivityModule::class])
    abstract fun contributeMyActivityFragment(): MyActivityFragment

    @Flow2ChildFragmentScope
    @ContributesAndroidInjector(modules = [MyForumsModule::class])
    abstract fun contributeMyForumsFragment(): MyForumsFragment
}