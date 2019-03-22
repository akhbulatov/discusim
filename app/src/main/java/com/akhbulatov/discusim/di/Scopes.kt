package com.akhbulatov.discusim.di

import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowFragmentScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class FlowChildFragmentScope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class Flow2ChildFragmentScope