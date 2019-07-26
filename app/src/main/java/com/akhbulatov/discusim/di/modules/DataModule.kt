package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.activity.ActivityRepositoryImpl
import com.akhbulatov.discusim.data.app.AppRepositoryImpl
import com.akhbulatov.discusim.data.comment.CommentRepositoryImpl
import com.akhbulatov.discusim.data.discussion.DiscussionRepositoryImpl
import com.akhbulatov.discusim.data.follower.FollowerRepositoryImpl
import com.akhbulatov.discusim.data.following.FollowingRepositoryImpl
import com.akhbulatov.discusim.data.forum.ForumRepositoryImpl
import com.akhbulatov.discusim.data.library.LibraryRepositoryImpl
import com.akhbulatov.discusim.data.moderator.ModeratorRepositoryImpl
import com.akhbulatov.discusim.data.session.SessionRepositoryImpl
import com.akhbulatov.discusim.data.topic.TopicRepositoryImpl
import com.akhbulatov.discusim.data.user.UserRepositoryImpl
import com.akhbulatov.discusim.domain.global.repositories.ActivityRepository
import com.akhbulatov.discusim.domain.global.repositories.AppRepository
import com.akhbulatov.discusim.domain.global.repositories.CommentRepository
import com.akhbulatov.discusim.domain.global.repositories.DiscussionRepository
import com.akhbulatov.discusim.domain.global.repositories.FollowerRepository
import com.akhbulatov.discusim.domain.global.repositories.FollowingRepository
import com.akhbulatov.discusim.domain.global.repositories.ForumRepository
import com.akhbulatov.discusim.domain.global.repositories.LibraryRepository
import com.akhbulatov.discusim.domain.global.repositories.ModeratorRepository
import com.akhbulatov.discusim.domain.global.repositories.SessionRepository
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
    abstract fun bindDiscussionRepository(repository: DiscussionRepositoryImpl): DiscussionRepository

    @Binds
    @Singleton
    abstract fun bindCommentRepository(repository: CommentRepositoryImpl): CommentRepository

    @Binds
    @Singleton
    abstract fun bindTopicRepository(repository: TopicRepositoryImpl): TopicRepository

    @Binds
    @Singleton
    abstract fun bindModeratorRepository(repository: ModeratorRepositoryImpl): ModeratorRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun bindFollowerRepository(repository: FollowerRepositoryImpl): FollowerRepository

    @Binds
    @Singleton
    abstract fun bindFollowingRepository(repository: FollowingRepositoryImpl): FollowingRepository

    @Binds
    @Singleton
    abstract fun bindAppRepository(repository: AppRepositoryImpl): AppRepository

    @Binds
    @Singleton
    abstract fun bindLibraryRepository(repository: LibraryRepositoryImpl): LibraryRepository
}
