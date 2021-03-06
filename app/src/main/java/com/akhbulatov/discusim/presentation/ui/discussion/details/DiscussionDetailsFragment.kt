package com.akhbulatov.discusim.presentation.ui.discussion.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.models.discussion.Discussion
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.HtmlTagUtils
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
import org.jetbrains.anko.support.v4.share
import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter
import javax.inject.Inject

class DiscussionDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_discussion_details

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: DiscussionDetailsViewModel by viewModels { viewModelFactory }

    private var discussion: Discussion? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val discussionId = requireNotNull(arguments?.getLong(ARG_DISCUSSION_ID))
        viewModel.setDiscussionId(discussionId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        authorImageView.setOnClickListener { viewModel.onAuthorClicked(discussion!!.author) }
        upvotesButton.setOnClickListener { viewModel.onVoteClicked(it.isSelected) }
        bindProgressButton(upvotesButton)
        errorRefreshButton.setOnClickListener { viewModel.loadDiscussionDetails() }
        observeUiChanges()
    }

    private fun setupToolbar() {
        with(toolbar) {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                discussion?.let { share(it.link) }
                true
            }
        }
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.discussion.observe(this, Observer { showDiscussionDetails(it.first, it.second) })
        viewModel.voteProgress.observe(this, Observer { showVoteProgress(it) })
        viewModel.voteError.observe(this, Observer { showVoteError(it) })
        viewModel.voteType.observe(this, Observer { updateVote(it) })
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
            this.discussion = discussion

            titleTextView.text = discussion.title
            authorImageView.loadRoundedImage(context, discussion.author.avatarUrl)
            authorTextView.text = discussion.author.name
            usernameTextView.text = getString(R.string.discussion_details_username, discussion.author.username)
            dateTextView.text = discussion.createdAt.getHumanCreatedTime(resources)

            if (discussion.message != null) {
                val message = HtmlTagUtils.replaceLinkTagsToImg(discussion.message)
                messageTextView.setHtml(message, HtmlHttpImageGetter(messageTextView, null, true))
            } else {
                messageTextView.isVisible = false
            }

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

            upvotesButton.setDiscussionVote(discussion.vote)
            commentsButton.text = discussion.comments.toString()
        }
        contentLayout.isVisible = show
    }

    private fun showVoteProgress(show: Boolean) {
        upvotesButton.showDiscussionVoteProgress(show, discussion!!.vote)
    }

    private fun showVoteError(message: String) {
        upvotesButton.resetDiscussionVoteBeforeProgress(discussion!!.vote)
        showSnackbar(message)
    }

    private fun updateVote(voteType: Vote.Type) {
        val newVote = Vote.createUpdatedInstance(discussion!!.vote.upvotes, voteType)
        upvotesButton.setDiscussionVote(newVote)

        discussion = discussion!!.copy(vote = newVote)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_DISCUSSION_ID = "discussion_id"

        fun newInstance(threadId: Long) = DiscussionDetailsFragment().apply {
            arguments = bundleOf(ARG_DISCUSSION_ID to threadId)
        }
    }
}
