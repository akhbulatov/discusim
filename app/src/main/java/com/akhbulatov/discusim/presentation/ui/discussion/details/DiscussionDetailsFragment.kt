package com.akhbulatov.discusim.presentation.ui.discussion.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Discussion
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.resetDiscussionVoteBeforeProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.setDiscussionVote
import com.akhbulatov.discusim.presentation.ui.global.utils.showDiscussionVoteProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import com.github.razir.progressbutton.bindProgressButton
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_discussion_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class DiscussionDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_discussion_details

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DiscussionDetailsViewModel by viewModels { viewModelFactory }

    private lateinit var vote: Vote

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val discussionId = requireNotNull(arguments?.getLong(ARG_DISCUSSION_ID))
        viewModel.setDiscussionId(discussionId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        voteButton.setOnClickListener { viewModel.onVoteClicked(it.isSelected) }
        bindProgressButton(voteButton)
        errorRefreshButton.setOnClickListener { viewModel.loadDiscussionDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.discussion.observe(this, Observer { showDiscussionDetails(it.first, it.second) })
        viewModel.voteProgress.observe(this, Observer { showVoteProgress(it) })
        viewModel.voteError.observe(this, Observer { showVoteError(it) })
        viewModel.vote.observe(this, Observer { updateVote(it) })
    }

    private fun showEmptyProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showEmptyError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showDiscussionDetails(show: Boolean, discussion: Discussion?) {
        if (discussion != null) {
            vote = discussion.vote

            titleTextView.text = discussion.title
            authorImageView.loadRoundedImage(context, discussion.author.avatarUrl)
            authorTextView.text = discussion.author.name
            usernameTextView.text = getString(R.string.discussion_details_username, discussion.author.username)
            dateTextView.text = discussion.createdAt.getHumanCreatedTime(resources)

            messageTextView.text = discussion.message?.parseAsHtml() // TODO
            if (discussion.topics.isNotEmpty()) {
                discussion.topics.forEach {
                    val topicChip = Chip(context, null, R.attr.topicChipStyle).apply {
                        text = it.name
                    }
                    topicChipGroup.addView(topicChip)
                }
            } else {
                topicChipGroup.isVisible = false
            }

            voteButton.setDiscussionVote(discussion.vote)
            commentsButton.text = discussion.comments.toString()
        }
        contentLayout.isVisible = show
    }

    private fun showVoteProgress(show: Boolean) {
        voteButton.showDiscussionVoteProgress(show, vote)
    }

    private fun showVoteError(message: String) {
        voteButton.resetDiscussionVoteBeforeProgress(vote)
        showSnackbar(message)
    }

    private fun updateVote(vote: Vote) {
        this.vote = vote
        voteButton.setDiscussionVote(vote)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_DISCUSSION_ID = "discussion_id"

        fun newInstance(threadId: Long) = DiscussionDetailsFragment().apply {
            arguments = bundleOf(ARG_DISCUSSION_ID to threadId)
        }
    }
}