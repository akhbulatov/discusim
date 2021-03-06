package com.akhbulatov.discusim.presentation.ui.forum.channel.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_channel_details_host.*
import javax.inject.Inject

class ChannelDetailsHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_channel_details_host

    @Inject lateinit var router: FlowRouter

    private lateinit var forumId: String
    private val pagerAdapter by lazy { ForumDetailsPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailsPager.adapter = pagerAdapter
    }

    override fun onBackPressed() = router.exit()

    inner class ForumDetailsPagerAdapter :
        FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.ForumDetails(forumId).fragment
            1 -> Screens.ChannelTopics(forumId).fragment
            2 -> Screens.ForumModerators(forumId).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.channel_details_host_details_tab)
            1 -> getString(R.string.channel_details_host_topics_tab)
            2 -> getString(R.string.channel_details_host_moderators_tab)
            else -> null
        }

        override fun getCount(): Int = 3
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ChannelDetailsHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
