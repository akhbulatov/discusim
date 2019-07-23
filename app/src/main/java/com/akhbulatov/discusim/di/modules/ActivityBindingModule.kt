package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.di.ActivityScope
import com.akhbulatov.discusim.presentation.ui.AppActivity
import com.akhbulatov.discusim.presentation.ui.AppActivityModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScope
    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    abstract fun contributeAppActivity(): AppActivity
}
