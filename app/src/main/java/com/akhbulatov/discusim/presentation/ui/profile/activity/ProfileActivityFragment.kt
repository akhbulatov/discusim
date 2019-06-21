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
import kotlinx.android.synthetic.main.layout_error.*
import kotlinx.android.synthetic.main.layout_progress.*
import kotlinx.android.synthetic.main.fragment_profile_activity.*
import javax.inject.Inject

class ProfileActivityFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_profile_activity

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileActivityViewModel
    private val activityAdapter by lazy { ProfileActivityAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = arguments?.getLong(ARG_USER_ID)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfileActivityViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(activityRecyclerView) {
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = activityAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.actions.observe(this, Observer { showActions(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showActions(actions: List<Action>) {
        activityAdapter.submitList(actions)
    }

    private fun showContentBlock(show: Boolean) {
        activityRecyclerView.isVisible = show
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
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long? = null) = ProfileActivityFragment().apply {
            userId?.let {
                arguments = bundleOf(ARG_USER_ID to userId)
            }
        }
    }
}