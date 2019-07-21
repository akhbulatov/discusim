package com.akhbulatov.discusim.presentation.ui.user.comments

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.comment.Comment
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.EndlessScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.VerticalSpaceItemDecoration
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.CommentAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_user_comments.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.dip
import javax.inject.Inject

class UserCommentsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_comments

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserCommentsViewModel by viewModels { viewModelFactory }

    private val commentAdapter by lazy { CommentAdapter() }
    private val onScrollListener by lazy {
        EndlessScrollListener(commentsRecyclerView.layoutManager as LinearLayoutManager)
        { viewModel.loadNextCommentsPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        commentsSwipeRefresh.setOnRefreshListener { viewModel.refreshComments() }
        with(commentsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(dip(6)))
            addOnScrollListener(onScrollListener)
            adapter = commentAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshComments() }
        dataRefreshButton.setOnClickListener { viewModel.refreshComments() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        commentsRecyclerView.removeOnScrollListener(onScrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.comments.observe(this, Observer { showComments(it.first, it.second) })
        viewModel.errorMessage.observe(this, Observer { showErrorMessage(it) })
        viewModel.refreshProgress.observe(this, Observer { showRefreshProgress(it) })
        viewModel.pageProgress.observe(this, Observer { showPageProgress(it) })
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

    private fun showComments(show: Boolean, comments: List<Comment>) {
        commentAdapter.submitList(comments)
        commentsRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        commentsSwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) onScrollListener.setLoaded()
        commentAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserCommentsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}