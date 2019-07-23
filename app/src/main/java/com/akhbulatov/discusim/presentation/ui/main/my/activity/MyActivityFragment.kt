package com.akhbulatov.discusim.presentation.ui.main.my.activity

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.EndlessScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.UserActivityAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_my_activity.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class MyActivityFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_my_activity

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MyActivityViewModel by viewModels { viewModelFactory }

    private val activityAdapter by lazy {
        UserActivityAdapter(
            { viewModel.onUserClicked(it) },
            { viewModel.onActionClicked(it) }
        )
    }
    private val onScrollListener by lazy {
        EndlessScrollListener(
            activityRecyclerView.layoutManager as LinearLayoutManager
        ) { viewModel.loadNextActivityPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        run { viewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activitySwipeRefresh.setOnRefreshListener { viewModel.refreshActivity() }
        with(activityRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(onScrollListener)
            adapter = activityAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshActivity() }
        dataRefreshButton.setOnClickListener { viewModel.refreshActivity() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        activityRecyclerView.removeOnScrollListener(onScrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.actions.observe(this, Observer { showActions(it.first, it.second) })
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

    private fun showActions(show: Boolean, actions: List<Action>) {
        activityAdapter.submitList(actions)
        activityRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        activitySwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) onScrollListener.setLoaded()
        activityAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}
