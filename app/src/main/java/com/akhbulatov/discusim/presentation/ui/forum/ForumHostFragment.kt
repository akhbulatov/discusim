package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_channel_host.toolbar
import kotlinx.android.synthetic.main.fragment_forum_host.*
import org.jetbrains.anko.support.v4.share
import javax.inject.Inject

class ForumHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_host

    @Inject lateinit var router: FlowRouter
    private val forumSharedViewModel: ForumSharedViewModel by viewModels()

    private lateinit var forumId: String
    private val forumPagerAdapter by lazy { ForumPagerAdapter() }
    private var sharedForum: ForumDetails? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        forumPager.adapter = forumPagerAdapter
        forumTabLayout.setupWithViewPager(forumPager)
        observeUiChanges()
    }

    private fun setupToolbar() {
        with(toolbar) {
            inflateMenu(R.menu.forum_channel_host)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                sharedForum?.let { share(it.webUrl) }
                true
            }
        }
    }

    private fun observeUiChanges() {
        forumSharedViewModel.forum.observe(this, Observer { forum ->
            this.sharedForum = forum
            toolbar.title = forum.name
        })
    }

    override fun onBackPressed() = router.exit()

    private inner class ForumPagerAdapter :
        FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.forum_host_details_tab)
            1 -> getString(R.string.forum_host_discussions_tab)
            2 -> getString(R.string.forum_host_moderators_tab)
            else -> throw IllegalArgumentException()
        }

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.ForumDetails(forumId).fragment
            1 -> Screens.ForumDiscussions(forumId).fragment
            2 -> Screens.ForumModerators(forumId).fragment
            else -> throw IllegalArgumentException()
        }

        override fun getCount(): Int = 3
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
