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
import com.akhbulatov.discusim.presentation.ui.global.widgets.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_threads.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_progress.*
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
        with(threadsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(dip(10)))
            adapter = threadsAdapter
        }
        observeUIChanges()
    }

    private fun observeUIChanges() {
        viewModel.threads.observe(this, Observer { showThreads(it) })
        viewModel.contentBlock.observe(this, Observer { showContent(it) })
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it) })
    }

    private fun showThreads(threads: List<Thread>) {
        threadsAdapter.submitList(threads)
    }

    private fun showContent(show: Boolean) {
        contentLayout.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(message: String) {
        errorLayout.isVisible = true
        errorTextView.text = message
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