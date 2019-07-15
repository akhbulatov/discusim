package com.akhbulatov.discusim.presentation.global

import androidx.fragment.app.Fragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFlowFragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFragment
import com.akhbulatov.discusim.presentation.ui.discussion.details.DiscussionDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.ForumFlowFragment
import com.akhbulatov.discusim.presentation.ui.forum.ForumHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsHostFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.moderators.ForumModeratorsFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.topics.ForumTopicsFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.DiscussionType
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsFragment
import com.akhbulatov.discusim.presentation.ui.forum.discussions.ForumDiscussionsHostFragment
import com.akhbulatov.discusim.presentation.ui.main.MainFlowFragment
import com.akhbulatov.discusim.presentation.ui.main.MainHostFragment
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityFragment
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsFragment
import com.akhbulatov.discusim.presentation.ui.main.my.profile.MyProfileFragment
import com.akhbulatov.discusim.presentation.ui.user.UserFlowFragment
import com.akhbulatov.discusim.presentation.ui.user.UserHostFragment
import com.akhbulatov.discusim.presentation.ui.user.activity.UserActivityFragment
import com.akhbulatov.discusim.presentation.ui.user.comments.UserCommentsFragment
import com.akhbulatov.discusim.presentation.ui.user.details.UserDetailsFragment
import com.akhbulatov.discusim.presentation.ui.user.followers.UserFollowersFragment
import com.akhbulatov.discusim.presentation.ui.user.following.UserFollowingFragment
import com.akhbulatov.discusim.presentation.ui.user.forums.UserForumsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {
    object AuthFlow : SupportAppScreen() {
        override fun getFragment(): Fragment = AuthFlowFragment()
    }

    object Auth : SupportAppScreen() {
        override fun getFragment(): Fragment = AuthFragment()
    }

    object MainFlow : SupportAppScreen() {
        override fun getFragment(): Fragment = MainFlowFragment()
    }

    object MainHost : SupportAppScreen() {
        override fun getFragment(): Fragment = MainHostFragment()
    }

    object MyActivity : SupportAppScreen() {
        override fun getFragment(): Fragment = MyActivityFragment()
    }

    object MyForums : SupportAppScreen() {
        override fun getFragment(): Fragment = MyForumsFragment()
    }

    object MyProfile : SupportAppScreen() {
        override fun getFragment(): Fragment = MyProfileFragment()
    }

    data class ForumFlow(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumFlowFragment.newInstance(forumId)
    }

    data class ForumHost(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumHostFragment.newInstance(forumId)
    }

    data class ForumDetailsHost(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDetailsHostFragment.newInstance(forumId)
    }

    data class ForumDetails(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDetailsFragment.newInstance(forumId)
    }

    data class ForumTopics(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumTopicsFragment.newInstance(forumId)
    }

    data class ForumModerators(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumModeratorsFragment.newInstance(forumId)
    }

    data class ForumDiscussionsHost(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDiscussionsHostFragment.newInstance(forumId)
    }

    data class ForumDiscussions(
        val forumId: String,
        val discussionType: DiscussionType = DiscussionType.LATEST
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDiscussionsFragment.newInstance(forumId, discussionType)
    }

    data class DiscussionDetails(
        val discussionId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = DiscussionDetailsFragment.newInstance(discussionId)
    }

    data class UserFlow(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserFlowFragment.newInstance(userId)
    }

    data class UserHost(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserHostFragment.newInstance(userId)
    }

    data class UserDetails(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserDetailsFragment.newInstance(userId)
    }

    data class UserActivity(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserActivityFragment.newInstance(userId)
    }

    data class UserComments(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserCommentsFragment.newInstance(userId)
    }

    data class UserForums(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserForumsFragment.newInstance(userId)
    }

    data class UserFollowers(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserFollowersFragment.newInstance(userId)
    }

    data class UserFollowing(
        val userId: Long
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = UserFollowingFragment.newInstance(userId)
    }
}