package com.akhbulatov.discusim.presentation.ui.forum.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.forum.ForumSharedViewModel
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.resetFollowBeforeProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.setFollow
import com.akhbulatov.discusim.presentation.ui.global.utils.showFollowProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import com.akhbulatov.discusim.presentation.ui.global.utils.showTextIfNotEmpty
import com.github.razir.progressbutton.bindProgressButton
import kotlinx.android.synthetic.main.fragment_forum_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class ForumDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_details

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ForumDetailsViewModel by viewModels { viewModelFactory }
    private val forumSharedViewModel: ForumSharedViewModel by viewModels(
        {
            if (forum.channel != null) {
                parentFragment!!.parentFragment!!
            } else {
                parentFragment!!
            }
        }
    )

    private lateinit var forum: ForumDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followButton.setOnClickListener { viewModel.onFollowClicked(it.isSelected, forum) }
        bindProgressButton(followButton)
        errorRefreshButton.setOnClickListener { viewModel.loadForumDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.forum.observe(this, Observer { showForumDetails(it.first, it.second) })
        viewModel.followProgress.observe(this, Observer { showFollowProgress(it) })
        viewModel.followError.observe(this, Observer { showFollowError(it) })
        viewModel.following.observe(this, Observer { updateFollow(it) })
    }

    private fun showEmptyProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showEmptyError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showForumDetails(show: Boolean, forum: ForumDetails?) {
        if (forum != null) {
            this.forum = forum
            forumSharedViewModel.forum.value = forum

            avatarImageView.loadRoundedImage(
                context,
                forum.channel?.avatarUrl ?: forum.faviconUrl,
                R.drawable.img_forum_placeholder
            )
            nameTextView.text = forum.name
            descriptionTextView.showTextIfNotEmpty(forum.description?.parseAsHtml()?.trim())

            if (forum.channel != null) {
                numDiscussionsTextView.text = getString(R.string.channel_details_thousand_nums, forum.numDiscussions)
                numFollowersTextView.text = getString(R.string.channel_details_thousand_nums, forum.numFollowers)
            } else {
                numDiscussionsLayout.isVisible = false
                numFollowersLayout.isVisible = false
            }

            followButton.setFollow(forum.following)
        }
        contentLayout.isVisible = show
    }

    private fun showFollowProgress(show: Boolean) {
        followButton.showFollowProgress(show)
    }

    private fun showFollowError(message: String) {
        followButton.resetFollowBeforeProgress()
        showSnackbar(message)
    }

    private fun updateFollow(forum: ForumDetails) {
        this.forum = forum

        followButton.setFollow(forum.following)
        if (forum.channel != null) {
            numFollowersTextView.text = getString(R.string.channel_details_thousand_nums, forum.numFollowers)
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumDetailsFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
