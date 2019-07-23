package com.akhbulatov.discusim.presentation.ui.user.details

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.getMediumDate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.resetFollowSmallBeforeProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.setFollowSmall
import com.akhbulatov.discusim.presentation.ui.global.utils.setForPrivateUser
import com.akhbulatov.discusim.presentation.ui.global.utils.setTintStartDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.showFollowSmallProgress
import com.akhbulatov.discusim.presentation.ui.global.utils.showSnackbar
import com.akhbulatov.discusim.presentation.ui.global.utils.showTextIfNotEmpty
import com.akhbulatov.discusim.presentation.ui.user.UserSharedViewModel
import com.github.razir.progressbutton.bindProgressButton
import kotlinx.android.synthetic.main.fragment_user_details.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import javax.inject.Inject

class UserDetailsFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_user_details

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: UserDetailsViewModel by viewModels { viewModelFactory }
    private val userSharedViewModel: UserSharedViewModel by viewModels({ parentFragment!! })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        followButton.setOnClickListener { viewModel.onFollowClicked(it.isSelected) }
        bindProgressButton(followButton)
        errorRefreshButton.setOnClickListener { viewModel.loadUserDetails() }
        observeUiChanges()
    }

    private fun observeUiChanges() {
        viewModel.emptyProgress.observe(this, Observer { showEmptyProgress(it) })
        viewModel.emptyError.observe(this, Observer { showEmptyError(it.first, it.second) })
        viewModel.user.observe(this, Observer { showUserDetails(it.first, it.second) })
        viewModel.followProgress.observe(this, Observer { showFollowProgress(it) })
        viewModel.followError.observe(this, Observer { showFollowError(it) })
        viewModel.follow.observe(this, Observer { updateFollow(true) })
        viewModel.unfollow.observe(this, Observer { updateFollow(false) })
    }

    private fun showEmptyProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showEmptyError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showUserDetails(show: Boolean, user: UserDetails?) {
        if (user != null) {
            userSharedViewModel.user.value = user

            avatarImageView.loadRoundedImage(context, user.avatarUrl)
            fullNameTextView.text = user.name
            usernameTextView.text = getString(R.string.user_details_username, user.username)
            if (!user.private) {
                // Скрывает кнопку для авторизованного юзера (т.е. для себя)
                if (!viewModel.isLoggedUser()) {
                    followButton.setFollowSmall(user.following)
                } else {
                    followButton.isVisible = false
                }
            } else {
                followButton.setForPrivateUser()
            }
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

    private fun showFollowProgress(show: Boolean) {
        followButton.showFollowSmallProgress(show)
    }

    private fun showFollowError(message: String) {
        followButton.resetFollowSmallBeforeProgress()
        showSnackbar(message)
    }

    private fun updateFollow(following: Boolean) {
        followButton.setFollowSmall(following)
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = UserDetailsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}
