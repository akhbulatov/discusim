package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.activity.ActivityRepositoryImpl
import com.akhbulatov.discusim.data.forum.ForumRepositoryImpl
import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.data.thread.ThreadRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
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
    abstract fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindThreadRepository(repository: ThreadRepositoryImpl): ThreadRepository

    @Binds
    @Singleton
    abstract fun bindForumRepository(repository: ForumRepositoryImpl): ForumRepository
}