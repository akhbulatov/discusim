package com.akhbulatov.discusim.presentation.ui.thread.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.getHumanCreatedTime
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setThreadVote
import com.google.android.material.chip.Chip
import kotlinx.android.synthetic.main.fragment_thread_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class ThreadDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_thread_details

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ThreadDetailsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val threadId = requireNotNull(arguments?.getLong(ARG_THREAD_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ThreadDetailsViewModel::class.java]
        viewModel.setThreadId(threadId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        errorRefreshButton.setOnClickListener { viewModel.loadThreadDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.thread.observe(this, Observer { showThreadDetails(it.first, it.second) })
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showThreadDetails(show: Boolean, thread: Thread?) {
        if (thread != null) {
            titleTextView.text = thread.title
            authorImageView.loadRoundedImage(context, thread.author.avatarUrl)
            authorTextView.text = thread.author.name
            usernameTextView.text = getString(R.string.thread_details_username, thread.author.username)
            dateTextView.text = thread.createdAt.getHumanCreatedTime(resources)

            messageTextView.text = thread.message?.parseAsHtml() // TODO
            if (thread.topics.isNotEmpty()) {
                thread.topics.forEach {
                    val topicChip = Chip(context, null, R.attr.topicChipStyle).apply {
                        text = it.name
                    }
                    topicChipGroup.addView(topicChip)
                }
            } else {
                topicChipGroup.isVisible = false
            }

            with(voteButton) {
                text = thread.upvotes.toString()
                setThreadVote(thread.isUpvoted)
            }
            commentsButton.text = thread.comments.toString()
        }
        contentLayout.isVisible = show
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_THREAD_ID = "thread_id"

        fun newInstance(threadId: Long) = ThreadDetailsFragment().apply {
            arguments = bundleOf(ARG_THREAD_ID to threadId)
        }
    }
}