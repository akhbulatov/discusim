package com.akhbulatov.discusim.presentation.ui.forum.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_forum_details_container.*
import javax.inject.Inject

class ForumDetailsContainerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_details_container

    @Inject lateinit var viewModelFactory: ViewModelFactory
    @Inject lateinit var router: FlowRouter

    private lateinit var forumId: String
    private val pagerAdapter by lazy { ForumDetailsPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupForumPager()
    }

    private fun setupForumPager() {
        detailsViewPager.adapter = pagerAdapter
        detailsTabLayout.setupWithViewPager(detailsViewPager)
    }

    override fun onBackPressed() = router.exit()

    inner class ForumDetailsPagerAdapter :
        FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.ForumDetails(forumId).fragment
            else -> Screens.ForumDetails(forumId).fragment
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.forum_details_container_details)
            else -> getString(R.string.forum_details_container_topics)
        }

        override fun getCount(): Int = 2
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumDetailsContainerFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}