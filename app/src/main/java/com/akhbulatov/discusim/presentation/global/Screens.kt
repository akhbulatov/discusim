package com.akhbulatov.discusim.presentation.global

import androidx.fragment.app.Fragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFlowFragment
import com.akhbulatov.discusim.presentation.ui.auth.AuthFragment
import com.akhbulatov.discusim.presentation.ui.forum.ForumFlowFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.details.ForumDetailsFragment
import com.akhbulatov.discusim.presentation.ui.forum.threads.ForumThreadsContainerFragment
import com.akhbulatov.discusim.presentation.ui.forum.threads.ForumThreadsFragment
import com.akhbulatov.discusim.presentation.ui.forum.threads.ThreadType
import com.akhbulatov.discusim.presentation.ui.main.MainFlowFragment
import com.akhbulatov.discusim.presentation.ui.main.my.activity.MyActivityFragment
import com.akhbulatov.discusim.presentation.ui.main.my.forums.MyForumsFragment
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

    object MyActivity : SupportAppScreen() {
        override fun getFragment(): Fragment = MyActivityFragment()
    }

    object MyForums : SupportAppScreen() {
        override fun getFragment(): Fragment = MyForumsFragment()
    }

    data class ForumFlow(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumFlowFragment.newInstance(forumId)
    }

    data class ForumDetailsContainer(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDetailsContainerFragment.newInstance(forumId)
    }

    data class ForumDetails(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumDetailsFragment.newInstance(forumId)
    }

    data class ForumThreadsContainer(
        val forumId: String
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumThreadsContainerFragment.newInstance(forumId)
    }

    data class ForumThreads(
        val forumId: String,
        val threadType: ThreadType = ThreadType.LATEST
    ) : SupportAppScreen() {
        override fun getFragment(): Fragment = ForumThreadsFragment.newInstance(forumId, threadType)
    }
}