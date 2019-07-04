package com.akhbulatov.discusim.presentation.ui.forum.threads

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.EndlessScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.VerticalSpaceItemDecoration
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.ThreadsAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_forum_threads.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

class ForumThreadsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_threads

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ForumThreadsViewModel
    private val threadsAdapter by lazy { ThreadsAdapter() }
    private val onScrollListener by lazy {
        EndlessScrollListener(threadsRecyclerView.layoutManager as LinearLayoutManager)
        { viewModel.loadNextThreadsPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        val threadType: ThreadType = requireNotNull(arguments?.getParcelable(ARG_THREAD_TYPE))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumThreadsViewModel::class.java]
        viewModel.setParams(forumId, threadType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        threadsSwipeRefresh.setOnRefreshListener { viewModel.refreshThreads() }
        with(threadsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(dip(10)))
            addOnScrollListener(onScrollListener)
            adapter = threadsAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshThreads() }
        dataRefreshButton.setOnClickListener { viewModel.refreshThreads() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        threadsRecyclerView.removeOnScrollListener(onScrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.threads.observe(this, Observer { showThreads(it.first, it.second) })
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

    private fun showThreads(show: Boolean, threads: List<Thread>) {
        threadsAdapter.submitList(threads)
        threadsRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        threadsSwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) onScrollListener.setLoaded()
        threadsAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"
        private const val ARG_THREAD_TYPE = "thread_type"

        fun newInstance(forumId: String, threadType: ThreadType) = ForumThreadsFragment().apply {
            arguments = bundleOf(
                ARG_FORUM_ID to forumId,
                ARG_THREAD_TYPE to threadType
            )
        }
    }
}