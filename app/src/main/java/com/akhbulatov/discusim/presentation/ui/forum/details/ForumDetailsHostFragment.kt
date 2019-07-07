package com.akhbulatov.discusim.presentation.ui.forum.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_forum_details_host.*
import javax.inject.Inject

class ForumDetailsHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_details_host

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
            1 -> Screens.ForumTopics(forumId).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.forum_details_host_details)
            1 -> getString(R.string.forum_details_host_topics)
            else -> null
        }

        override fun getCount(): Int = 2
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumDetailsHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}