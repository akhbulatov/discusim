package com.akhbulatov.discusim.presentation.ui.threads

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Thread
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.widgets.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_threads.*
import javax.inject.Inject

class ThreadsFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ThreadsViewModel
    private val threadsAdapter by lazy { ThreadsAdapter() }

    override val layoutRes: Int = R.layout.fragment_threads

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        val threadType: ThreadType = requireNotNull(arguments?.getParcelable(ARG_THREAD_TYPE))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ThreadsViewModel::class.java]
        viewModel.setParams(forumId, threadType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        threadsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = threadsAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.threads.observe(this, Observer { showThreads(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showThreads(threads: List<Thread>) {
        threadsAdapter.submitList(threads)
    }

    private fun showContentBlock(show: Boolean) {
        threadsRecyclerView.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        contentProgressLayout.isVisible = show
    }

    private fun showError(message: String) {
        contentErrorLayout.isVisible = true
        contentErrorTextView.text = message
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"
        private const val ARG_THREAD_TYPE = "thread_type"

        fun newInstance(forumId: String, threadType: ThreadType) = ThreadsFragment().apply {
            arguments = bundleOf(
                ARG_FORUM_ID to forumId,
                ARG_THREAD_TYPE to threadType
            )
        }
    }
}