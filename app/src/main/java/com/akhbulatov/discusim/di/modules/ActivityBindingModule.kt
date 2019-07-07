package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.di.ActivityScope
import com.akhbulatov.discusim.presentation.ui.RootActivity
import com.akhbulatov.discusim.presentation.ui.RootModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [RootModule::class])
    abstract fun contributeRootActivity(): RootActivity
}