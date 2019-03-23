package com.akhbulatov.discusim.presentation.global

import androidx.fragment.app.Fragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFlowFragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFragment
import com.akhbulatov.discusim.presentation.ui.channel.ChannelFragment
import com.akhbulatov.discusim.presentation.ui.channeldetails.ChannelDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.ForumFragment
import com.akhbulatov.discusim.presentation.ui.forums.ForumsFragment
import com.akhbulatov.discusim.presentation.ui.main.MainFlowFragment
import com.akhbulatov.discusim.presentation.ui.profile.ProfileFragment
import com.akhbulatov.discusim.presentation.ui.profile.activities.ProfileActivitiesFragment
import com.akhbulatov.discusim.presentation.ui.profile.posts.ProfilePostsFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadType
import com.akhbulatov.discusim.presentation.ui.threads.ThreadsFragment
import com.akhbulatov.discusim.presentation.ui.users.UserType
import com.akhbulatov.discusim.presentation.ui.users.UsersFragment
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

    data class Profile(val userId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = ProfileFragment.newInstance(userId)
    }

    data class ProfileActivities(val userId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = ProfileActivitiesFragment.newInstance(userId)
    }

    data class ProfilePosts(val userId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = ProfilePostsFragment.newInstance(userId)
    }

    data class Users(val itemId: String, val userType: UserType) : SupportAppScreen() {
        override fun getFragment(): Fragment = UsersFragment.newInstance(itemId, userType)
    }

    data class Forum(val forumId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumFragment.newInstance(forumId)
    }

    data class Forums(val userId: Long) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumsFragment.newInstance(userId)
    }

    data class Channel(val forumId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment = ChannelFragment.newInstance(forumId)
    }

    data class ChannelDetails(val forumId: String) : SupportAppScreen() {
        override fun getFragment(): Fragment = ChannelDetailsFragment.newInstance(forumId)
    }

    data class Threads(val forumId: String, val threadType: ThreadType = ThreadType.LATEST) : SupportAppScreen() {
        override fun getFragment(): Fragment = ThreadsFragment.newInstance(forumId, threadType)
    }
}