package com.akhbulatov.discusim.presentation.ui.user.followers

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.user.User
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.list.EndlessScrollListener
import com.akhbulatov.discusim.presentation.ui.global.list.adapters.UserAdapter
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import kotlinx.android.synthetic.main.fragment_user_followers.*
import kotlinx.android.synthetic.main.layout_empty_data.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class UserFollowersFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_followers

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: UserFollowersViewModel by viewModels { viewModelFactory }

    private val userAdapter by lazy {
        UserAdapter { viewModel.onFollowerClicked(it) }
    }
    private val onScrollListener by lazy {
        EndlessScrollListener(followersRecyclerView.layoutManager as LinearLayoutManager) { viewModel.loadNextFollowersPage() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followersSwipeRefresh.setOnRefreshListener { viewModel.refreshFollowers() }
        with(followersRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            addOnScrollListener(onScrollListener)
            adapter = userAdapter
        }
        errorRefreshButton.setOnClickListener { viewModel.refreshFollowers() }
        dataRefreshButton.setOnClickListener { viewModel.refreshFollowers() }
        observeUiChanges()
    }

    override fun onDestroyView() {
        followersRecyclerView.removeOnScrollListener(onScrollListener)
        super.onDestroyView()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.emptyData.observe(this, Observer { showEmptyData(it) })
        viewModel.followers.observe(this, Observer { showFollowers(it.first, it.second) })
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

    private fun showFollowers(show: Boolean, followers: List<User>) {
        userAdapter.submitList(followers)
        followersRecyclerView.isVisible = show
    }

    private fun showErrorMessage(message: String) {
        showSnackbar(message)
    }

    private fun showRefreshProgress(show: Boolean) {
        followersSwipeRefresh.isRefreshing = show
    }

    private fun showPageProgress(show: Boolean) {
        if (!show) onScrollListener.setLoaded()
        userAdapter.showProgress(show)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserFollowersFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}
