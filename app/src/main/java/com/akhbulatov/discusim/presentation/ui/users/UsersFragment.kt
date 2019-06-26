package com.akhbulatov.discusim.presentation.ui.users

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_users.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class UsersFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: UsersViewModel
    private val usersAdapter by lazy { UsersAdapter() }

    override val layoutRes: Int = R.layout.fragment_users

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val itemId = requireNotNull(arguments?.getString(ARG_ITEM_ID))
        val userType: UserType = requireNotNull(arguments?.getParcelable(ARG_USER_TYPE))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UsersViewModel::class.java]
        viewModel.setInitialData(itemId, userType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        usersRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = usersAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.users.observe(this, Observer { showUsers(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showUsers(users: List<User>) {
        usersAdapter.submitList(users)
    }

    private fun showContentBlock(show: Boolean) {
        usersRecyclerView.isVisible = show
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
        private const val ARG_ITEM_ID = "item_id"
        private const val ARG_USER_TYPE = "user_type"

        fun newInstance(itemId: String, userType: UserType) = UsersFragment().apply {
            arguments = bundleOf(
                ARG_ITEM_ID to itemId,
                ARG_USER_TYPE to userType
            )
        }
    }
}