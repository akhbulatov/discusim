package com.akhbulatov.discusim.presentation.ui.forum.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.forum.ForumSharedViewModel
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.loadImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setFollow
import kotlinx.android.synthetic.main.fragment_forum_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class ForumDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_details

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ForumDetailsViewModel
    private lateinit var forumSharedViewModel: ForumSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumDetailsViewModel::class.java]
        viewModel.setForumId(forumId)

        val parentFlowFragment = parentFragment!!.parentFragment!!
        forumSharedViewModel = ViewModelProviders.of(parentFlowFragment)[ForumSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorRefreshButton.setOnClickListener { viewModel.loadForumDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.forum.observe(this, Observer { showForumDetails(it.first, it.second) })
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showForumDetails(show: Boolean, forum: Forum?) {
        if (forum != null) {
            forumSharedViewModel.forum.value = forum

            avatarImageView.loadImage(context, forum.channel?.avatarUrl ?: forum.faviconUrl)
            nameTextView.text = forum.name
            descriptionTextView.text = forum.description
            numThreadsTextView.text = forum.numThreads.toString()
            numFollowersTextView.text = forum.numFollowers.toString()
            followButton.setFollow(forum.isFollowing)
        }
        contentLayout.isVisible = show
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumDetailsFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}