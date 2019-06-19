package com.akhbulatov.discusim.presentation.ui.profile

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ProfileFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfileViewModel

    override val layoutRes: Int = R.layout.fragment_profile

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfileViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.profile.observe(this, Observer { showProfile(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showProfile(userDetails: UserDetails) {
        userDetails.let {
            toolbar.title = userDetails.username

            Glide.with(this@ProfileFragment)
                .load(it.avatar.large.link)
                .placeholder(R.drawable.img_user_placeholder)
                .into(avatarImageView)

            nameTextView.text = it.name
            usernameTextView.text = it.username
            aboutTextView.text = it.about
            upvotesTextView.text = it.upvotes.toString()
            locationTextView.text = it.location
            websiteTextView.text = it.url
            joinedAtTextView.text = it.joinedAt
        }
    }

    private fun showContentBlock(show: Boolean) {
        contentLayout.isVisible = show
    }

    private fun showProgress(show: Boolean) {
        contentProgressLayout.isVisible = show
    }

    private fun showError(message: String) {
        contentErrorLayout.isVisible = true
        contentErrorTextView.text = message
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_USER_ID = "user_id"

        fun newInstance(userId: Long) = ProfileFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}