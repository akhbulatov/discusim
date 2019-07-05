package com.akhbulatov.discusim.presentation.ui.user.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.getMediumDate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setTintStartDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.showTextIfNotEmpty
import com.akhbulatov.discusim.presentation.ui.user.UserSharedViewModel
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class UserDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_details

    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: UserDetailsViewModel
    private lateinit var userSharedViewModel: UserSharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[UserDetailsViewModel::class.java]
        viewModel.setUserId(userId)

        userSharedViewModel = ViewModelProviders.of(parentFragment!!)[UserSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorRefreshButton.setOnClickListener { viewModel.loadUserDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.user.observe(this, Observer { showUserDetails(it.first, it.second) })
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showUserDetails(show: Boolean, user: User?) {
        if (user != null) {
            userSharedViewModel.user.value = user

            avatarImageView.loadRoundedImage(context, user.avatarUrl)
            fullNameTextView.text = user.name
            usernameTextView.text = getString(R.string.user_details_username, user.username)
            aboutTextView.showTextIfNotEmpty(user.about)
            with(numUpvotesTextView) {
                setTintStartDrawable(R.color.accent)
                text = getString(R.string.user_details_num_upvotes, user.numUpvotes).parseAsHtml()
            }
            with(locationTextView) {
                setTintStartDrawable(R.color.accent)
                showTextIfNotEmpty(user.location)
            }
            with(websiteTextView) {
                setTintStartDrawable(R.color.accent)
                showTextIfNotEmpty(user.website)
            }
            with(joinedAtTextView) {
                setTintStartDrawable(R.color.accent)
                text = getString(R.string.user_details_joined_date, user.joinedAt.getMediumDate())
            }
        }
        contentLayout.isVisible = show
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}