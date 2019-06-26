package com.akhbulatov.discusim.presentation.ui.threads

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import com.akhbulatov.discusim.presentation.ui.global.views.list.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_threads.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.support.v4.dip
import javax.inject.Inject

class ThreadsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_threads

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ThreadsViewModel
    private val threadsAdapter by lazy { ThreadsAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = arguments?.getString(ARG_FORUM_ID)
        val threadType: ThreadType = requireNotNull(arguments?.getParcelable(ARG_THREAD_TYPE))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ThreadsViewModel::class.java]
        viewModel.setParams(forumId, threadType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        threadsSwipeRefresh.setOnRefreshListener { viewModel.refreshThreads() }
        with(threadsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(
                VerticalSpaceItemDecoration(
                    dip(
                        10
                    )
                )
            )
            adapter = threadsAdapter
        }
        observeUIChanges()
    }

    private fun observeUIChanges() {
        viewModel.threads.observe(this, Observer { showThreads(it) })
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.refreshProgress.observe(this, Observer { showRefreshProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.refreshError.observe(this, Observer { showRefreshError(it) })
    }

    private fun showThreads(threads: List<Thread>) {
        threadsAdapter.submitList(threads)
    }

    private fun showProgress(show: Boolean) {
        emptyProgressLayout.isVisible = show
    }

    private fun showRefreshProgress(show: Boolean) {
        threadsSwipeRefresh.isRefreshing = show
    }

    private fun showError(show: Boolean, message: String?) {
        emptyErrorLayout.isVisible = show
        errorTextView.text = message
    }

    private fun showRefreshError(message: String) {
        showSnackbar(message)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"
        private const val ARG_THREAD_TYPE = "thread_type"

        fun newInstance(forumId: String? = null, threadType: ThreadType) = ThreadsFragment().apply {
            arguments = bundleOf(
                ARG_FORUM_ID to forumId,
                ARG_THREAD_TYPE to threadType
            )
        }
    }
}