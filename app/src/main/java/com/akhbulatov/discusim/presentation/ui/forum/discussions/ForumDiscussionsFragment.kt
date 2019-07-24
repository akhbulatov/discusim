package com.akhbulatov.discusim.presentation.ui.forum.discussions

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Vote
import com.akhbulatov.discusim.domain.global.models.discussion.Discussion
import com.akhbulatov.discusim.domain.global.models.forum.Forum
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.EndlessScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.VerticalSpaceItemDecoration
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.DiscussionAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_forum_discussions.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

class ForumDiscussionsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_discussions

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: ForumDiscussionsViewModel by viewModels { viewModelFactory }
    private val discussionSharedViewModel: DiscussionSharedViewModel by viewModels(
        {
            if (Forum.isChannel(forumId)) {
                parentFragment!!.parentFragment!!.parentFragment!!
            } else {
                parentFragment!!.parentFragment!!
            }
        }
    )

    private lateinit var forumId: String
    private var discussionPosition = -1

    private val discussionAdapter by lazy {
        DiscussionAdapter(
            this,
            { view, discussion, position ->
                discussionPosition = position
                viewModel.onVoteClicked(discussion, view.isSelected)
            },
            { discussion, position ->
                discussionPosition = position
                viewModel.onDiscussionClicked(discussion)
            }
        )
    }
    private val onScrollListener by lazy {
        EndlessScrollListener(
            discussionsRecyclerView.layoutManager as LinearLayoutManager
        ) { viewModel.loadNextDiscussionsPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        val discussionType: DiscussionType = requireNotNull(arguments?.getParcelable(ARG_DISCUSSION_TYPE))
        viewModel.setParams(forumId, discussionType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        discussionsSwipeRefresh.setOnRefreshListener { viewModel.refreshDiscussions() }
        with(discussionsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(dip(10)))
            addOnScrollListener(onScrollListener)
            adapter = discussionAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshDiscussions() }
        dataRefreshButton.setOnClickListener { viewModel.refreshDiscussions() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        discussionsRecyclerView.removeOnScrollListener(onScrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.discussions.observe(this, Observer { showDiscussions(it.first, it.second) })
        viewModel.errorMessage.observe(this, Observer { showErrorMessage(it) })
        viewModel.refreshProgress.observe(this, Observer { showRefreshProgress(it) })
        viewModel.pageProgress.observe(this, Observer { showPageProgress(it) })
        viewModel.voteType.observe(this, Observer { updateVote(it) })
        viewModel.voteError.observe(this, Observer { showVoteError(it) })
        discussionSharedViewModel.discussion.observe(this, Observer { updateDiscussion(it) })
    }

    private fun showEmptyProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showEmptyError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showEmptyData(show: Boolean) {
        dataLayout.isVisible = show
    }

    private fun showDiscussions(show: Boolean, discussions: List<Discussion>) {
        discussionAdapter.submitList(discussions)
        discussionsRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        discussionsSwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) onScrollListener.setLoaded()
        discussionAdapter.showProgress(show)
    }

    private fun updateDiscussion(discussion: Discussion) {
        if (discussionPosition >= 0) {
            val items = discussionAdapter.currentList.toMutableList()
            items[discussionPosition] = discussion
            discussionAdapter.submitList(items)
        }
    }

    private fun updateVote(voteType: Vote.Type) {
        if (discussionPosition >= 0) {
            val items = discussionAdapter.currentList.toMutableList()
            val item = items[discussionPosition] as Discussion
            val newVote = Vote.createUpdatedInstance(item.vote.upvotes, voteType)
            items[discussionPosition] = item.copy(vote = newVote)
            discussionAdapter.submitList(items)
        }
    }

    private fun showVoteError(message: String) {
        if (discussionPosition >= 0) {
            discussionAdapter.notifyItemChanged(discussionPosition)
        }
        showSnackbar(message)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"
        private const val ARG_DISCUSSION_TYPE = "discussion_type"

        fun newInstance(forumId: String, discussionType: DiscussionType) = ForumDiscussionsFragment().apply {
            arguments = bundleOf(
                ARG_FORUM_ID to forumId,
                ARG_DISCUSSION_TYPE to discussionType
            )
        }
    }
}
