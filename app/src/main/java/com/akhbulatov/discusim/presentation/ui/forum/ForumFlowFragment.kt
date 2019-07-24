package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.forum.discussions.DiscussionSharedViewModel
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ForumFlowFragment : FlowFragment() {
    private lateinit var forumId: String

    private val discussionSharedViewModel: DiscussionSharedViewModel by viewModels()

    override fun getLaunchScreen(): SupportAppScreen =
        if (Forum.isChannel(forumId)) {
            Screens.ChannelHost(forumId)
        } else {
            Screens.ForumHost(forumId)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        super.onCreate(savedInstanceState)
        run { discussionSharedViewModel }
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumFlowFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
