package com.akhbulatov.discusim.presentation.ui.forum.channel.discussions

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.forum.discussions.DiscussionType
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_channel_discussions_host.*
import javax.inject.Inject

class ChannelDiscussionsHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_channel_discussions_host

    @Inject lateinit var router: FlowRouter

    private lateinit var forumId: String
    private val pagerAdapter by lazy { ForumDiscussionsPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discussionsPager.adapter = pagerAdapter
    }

    override fun onBackPressed() = router.exit()

    inner class ForumDiscussionsPagerAdapter :
        FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.ForumDiscussions(forumId).fragment
            1 -> Screens.ForumDiscussions(forumId, DiscussionType.HOT).fragment
            2 -> Screens.ForumDiscussions(forumId, DiscussionType.POPULAR).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.channel_discussions_host_latest)
            1 -> getString(R.string.channel_discussions_host_hot)
            2 -> getString(R.string.channel_discussions_host_popular)
            else -> null
        }

        override fun getCount(): Int = 3
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ChannelDiscussionsHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
