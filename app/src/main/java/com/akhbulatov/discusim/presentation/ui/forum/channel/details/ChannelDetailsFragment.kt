package com.akhbulatov.discusim.presentation.ui.forum.channel.details

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
import kotlinx.android.synthetic.main.fragment_channel_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class ChannelDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_channel_details

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ChannelDetailsViewModel by viewModels { viewModelFactory }
    private val forumSharedViewModel: ForumSharedViewModel by viewModels({ parentFragment!!.parentFragment!! })

    private lateinit var forum: ForumDetails

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followButton.setOnClickListener { viewModel.onFollowClicked(it.isSelected) }
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
        viewModel.follow.observe(this, Observer { updateFollow(true) })
        viewModel.unfollow.observe(this, Observer { updateFollow(false) })
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
            numDiscussionsTextView.text = getString(R.string.channel_details_thousand_nums, forum.numDiscussions)
            numFollowersTextView.text = getString(R.string.channel_details_thousand_nums, forum.numFollowers)
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

    private fun updateFollow(following: Boolean) {
        followButton.setFollow(following)
        updateNumFollowers(following)
    }

    private fun updateNumFollowers(following: Boolean) {
        val oldFollowers = forum.numFollowers
        val newFollowers = if (following) oldFollowers + 1 else oldFollowers - 1
        forum = forum.copy(numFollowers = newFollowers)
        numFollowersTextView.text = getString(R.string.channel_details_thousand_nums, newFollowers)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ChannelDetailsFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
