package com.akhbulatov.discusim.presentation.ui.main.my.profile

import android.os.Bundle
import android.view.View
import androidx.core.text.parseAsHtml
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.getMediumDate
import com.akhbulatov.discusim.presentation.ui.global.utils.loadRoundedImage
import com.akhbulatov.discusim.presentation.ui.global.utils.setTintStartDrawable
import com.akhbulatov.discusim.presentation.ui.global.utils.showTextIfNotEmpty
import kotlinx.android.synthetic.main.fragment_my_profile.*
import kotlinx.android.synthetic.main.layout_empty_error.*
import kotlinx.android.synthetic.main.layout_empty_progress.*
import org.jetbrains.anko.support.v4.share
import javax.inject.Inject

class MyProfileFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_my_profile

    @Inject lateinit var viewModelFactory: ViewModelFactory
    private val viewModel: MyProfileViewModel by viewModels { viewModelFactory }

    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        run { viewModel }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        errorRefreshButton.setOnClickListener { viewModel.loadMyDetails() }
        observeUiChanges()
    }

    private fun setupToolbar() {
        with(toolbar) {
            inflateMenu(R.menu.my_profile)
            setOnMenuItemClickListener {
                user?.let { share(it.webUrl) }
                true
            }
        }
    }

    private fun observeUiChanges() {
        viewModel.progress.observe(this, Observer { showProgress(it) })
        viewModel.error.observe(this, Observer { showError(it.first, it.second) })
        viewModel.user.observe(this, Observer { showMyDetails(it.first, it.second) })
    }

    private fun showProgress(show: Boolean) {
        progressLayout.isVisible = show
    }

    private fun showError(show: Boolean, message: String?) {
        errorTextView.text = message
        errorLayout.isVisible = show
    }

    private fun showMyDetails(show: Boolean, user: User?) {
        if (user != null) {
            this.user = user

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
}