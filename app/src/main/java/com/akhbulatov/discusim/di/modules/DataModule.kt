package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.forums.ForumsRepositoryImpl
import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.data.users.UsersRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import com.akhbulatov.discusim.domain.global.repositories.UsersRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindSessionRepository(repositoryImpl: SessionRepositoryImpl): SessionRepository

    @Binds
    @Singleton
    abstract fun bindUsersRepository(repository: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    abstract fun bindForumsRepository(repository: ForumsRepositoryImpl): ForumsRepository
}