package com.akhbulatov.discusim.presentation.ui.profile.activity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_user_activity.*
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_progress.*
import javax.inject.Inject

class UserActivityFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_activity

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UserActivityViewModel
    private val activityAdapter by lazy { UserActivityAdapter() }

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
            adapter = activityAdapter
        }
        observeUIChanges()
    }

    private fun observeUIChanges() {
        viewModel.actions.observe(this, Observer { showActions(it) })
        viewModel.content.observe(this, Observer { showContent(it) })
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.refreshProgress.observe(this, Observer { showRefreshProgress(it) })
        viewModel.error.observe(this, Observer { showError(it) })
        viewModel.refreshError.observe(this, Observer { showRefreshError(it) })
    }

    private fun showActions(actions: List<Action>) {
        activityAdapter.submitList(actions)
    }

    private fun showContent(show: Boolean) {
        contentLayout.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showRefreshProgress(show: Boolean) {
        activitySwipeRefresh.isRefreshing = show
    }

    private fun showError(message: String) {
        errorLayout.isVisible = true
        errorTextView.text = message
    }

    private fun showRefreshError(message: String) {
        showSnackbar(message)
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