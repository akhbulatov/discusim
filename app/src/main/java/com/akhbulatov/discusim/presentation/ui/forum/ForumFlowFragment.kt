package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import androidx.core.os.bundleOf
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment

class ForumFlowFragment : FlowFragment() {
    override val layoutRes: Int = R.layout.layout_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        router.newRootScreen(Screens.ForumContainer(forumId))
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumFlowFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}