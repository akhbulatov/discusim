package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.threads.ThreadType
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_forum_container.*
import javax.inject.Inject

class ForumContainerFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_container

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var forumId: String
    private lateinit var viewModel: ForumContainerViewModel
    private val forumPagerAdapter by lazy { ForumPagerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumContainerViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        setupForumPager()
        observeUIChanges()
    }

    private fun setupForumPager() {
        forumPager.adapter = forumPagerAdapter
        forumTabLayout.setupWithViewPager(forumPager)
    }

    private fun observeUIChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.forum.observe(this, Observer { showForumDetails(it) })
    }

    private fun showProgress(show: Boolean) {
    }

    private fun showError(show: Boolean, message: String?) {
    }

    private fun showForumDetails(forum: Forum) {
        collapsingToolbar.title = forum.name
        Glide.with(this)
            .load(forum.channel?.bannerUrl)
            .into(bannerImageView)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    inner class ForumPagerAdapter : FragmentPagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getItem(position: Int): Fragment = when (position) {
            0 -> Screens.ForumDetails(forumId).fragment
            1 -> Screens.Threads(forumId, ThreadType.LATEST).fragment
            else -> Screens.Threads(forumId, ThreadType.HOT).fragment
        }

        override fun getPageTitle(position: Int): CharSequence? = when (position) {
            0 -> getString(R.string.forum_container_tab_details)
            1 -> getString(R.string.forum_container_tab_latest)
            else -> getString(R.string.forum_container_tab_hot)
        }

        override fun getCount(): Int = 3
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumContainerFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}