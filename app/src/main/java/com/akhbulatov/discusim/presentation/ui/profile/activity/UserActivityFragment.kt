package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import com.akhbulatov.discusim.presentation.ui.global.views.list.InfiniteScrollListener
import kotlinx.android.synthetic.main.fragment_user_activity.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class UserActivityFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_activity

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserActivityViewModel
    private val activityAdapter by lazy { UserActivityAdapter() }
    private val scrollListener by lazy {
        InfiniteScrollListener(activityRecyclerView.layoutManager as LinearLayoutManager)
        { viewModel.loadNextActivityPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getLong(ARG_USER_ID)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserActivityViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activitySwipeRefresh.setOnRefreshListener { viewModel.refreshActivity() }
        with(activityRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(scrollListener)
            adapter = activityAdapter
        }
        observeUIChanges()
    }

    private fun observeUIChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.actions.observe(this, Observer { showActions(it.first, it.second) })
        viewModel.errorMessage.observe(this, Observer { showErrorMessage(it) })
        viewModel.refreshProgress.observe(this, Observer { showRefreshProgress(it) })
        viewModel.pageProgress.observe(this, Observer { showPageProgress(it) })
    }

    private fun showEmptyProgress(show: Boolean) {
        emptyProgressLayout.isVisible = show
    }

    private fun showEmptyError(show: Boolean, message: String?) {
        errorTextView.text = message
        emptyErrorLayout.isVisible = show
    }

    private fun showEmptyData(show: Boolean) {
        emptyDataLayout.isVisible = show
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
        if (!show) scrollListener.setLoaded()
        activityAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long? = null) = UserActivityFragment().apply {
            userId?.let {
                arguments = bundleOf(ARG_USER_ID to userId)
            }
        }
    }
}