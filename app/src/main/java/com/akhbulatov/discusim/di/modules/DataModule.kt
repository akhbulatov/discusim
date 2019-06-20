package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.activity.ActivityRepositoryImpl
import com.akhbulatov.discusim.data.forums.ForumsRepositoryImpl
import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.data.thread.ThreadsRepositoryImpl
import com.akhbulatov.discusim.data.user.UserRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import com.akhbulatov.discusim.domain.global.repositories.ForumsRepository
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import com.akhbulatov.discusim.domain.global.repositories.ThreadsRepository
import com.akhbulatov.discusim.domain.global.repositories.UserRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {
    @Binds
    @Singleton
    abstract fun bindSessionRepository(repository: SessionRepositoryImpl): SessionRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindForumsRepository(repository: ForumsRepositoryImpl): ForumsRepository

    @Binds
    @Singleton
    abstract fun bindThreadsRepository(repository: ThreadsRepositoryImpl): ThreadsRepository
}