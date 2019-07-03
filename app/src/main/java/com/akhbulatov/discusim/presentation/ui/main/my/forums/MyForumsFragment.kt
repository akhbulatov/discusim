package com.akhbulatov.discusim.presentation.ui.main.my.forums

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.InfiniteScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.ForumsAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_my_forums.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class MyForumsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_my_forums

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MyForumsViewModel
    private val forumsAdapter by lazy {
        ForumsAdapter { viewModel.onForumClicked(it) }
    }
    private val scrollListener by lazy {
        InfiniteScrollListener(forumsRecyclerView.layoutManager as LinearLayoutManager)
        { viewModel.loadNextForumsPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[MyForumsViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        forumsSwipeRefresh.setOnRefreshListener { viewModel.refreshForums() }
        with(forumsRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(scrollListener)
            adapter = forumsAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshForums() }
        dataRefreshButton.setOnClickListener { viewModel.refreshForums() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        forumsRecyclerView.removeOnScrollListener(scrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.forums.observe(this, Observer { showForums(it.first, it.second) })
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

    private fun showForums(show: Boolean, forums: List<Forum>) {
        forumsAdapter.submitList(forums)
        forumsRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        forumsSwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) scrollListener.setLoaded()
        forumsAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}