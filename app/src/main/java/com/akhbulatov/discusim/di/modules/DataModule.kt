package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindSessionRepository(repositoryImpl: SessionRepositoryImpl): SessionRepository
}