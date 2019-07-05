package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.activity.ActivityRepositoryImpl
import com.akhbulatov.discusim.data.comment.CommentRepositoryImpl
import com.akhbulatov.discusim.data.follower.FollowerRepositoryImpl
import com.akhbulatov.discusim.data.following.FollowingRepositoryImpl
import com.akhbulatov.discusim.data.forum.ForumRepositoryImpl
import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.data.thread.ThreadRepositoryImpl
import com.akhbulatov.discusim.data.topic.TopicRepositoryImpl
import com.akhbulatov.discusim.data.user.UserRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import com.akhbulatov.discusim.domain.global.repositories.CommentRepository
import com.akhbulatov.discusim.domain.global.repositories.FollowerRepository
import com.akhbulatov.discusim.domain.global.repositories.FollowingRepository
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
import com.akhbulatov.discusim.domain.global.repositories.ThreadRepository
import com.akhbulatov.discusim.domain.global.repositories.TopicRepository
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
    abstract fun bindActivityRepository(repository: ActivityRepositoryImpl): ActivityRepository

    @Binds
    @Singleton
    abstract fun bindForumRepository(repository: ForumRepositoryImpl): ForumRepository

    @Binds
    @Singleton
    abstract fun bindThreadRepository(repository: ThreadRepositoryImpl): ThreadRepository

    @Binds
    @Singleton
    abstract fun bindCommentRepository(repository: CommentRepositoryImpl): CommentRepository

    @Binds
    @Singleton
    abstract fun bindTopicRepository(repository: TopicRepositoryImpl): TopicRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindFollowerRepository(repository: FollowerRepositoryImpl): FollowerRepository

    @Binds
    @Singleton
    abstract fun bindFollowingRepository(repository: FollowingRepositoryImpl): FollowingRepository
}