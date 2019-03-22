package com.akhbulatov.discusim.presentation.ui.profile.posts

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Post
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.global.widgets.VerticalSpaceItemDecoration
import kotlinx.android.synthetic.main.content_error.*
import kotlinx.android.synthetic.main.content_progress.*
import kotlinx.android.synthetic.main.fragment_profile_posts.*
import javax.inject.Inject

class ProfilePostsFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: ProfilePostsViewModel
    private val postsAdapter by lazy { ProfilePostsAdapter() }

    override val layoutRes: Int = R.layout.fragment_profile_posts

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId = requireNotNull(arguments?.getLong(ARG_USER_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ProfilePostsViewModel::class.java]
        viewModel.setUserId(userId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postsRecyclerView.run {
            setHasFixedSize(true)
            addItemDecoration(VerticalSpaceItemDecoration(20))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postsAdapter
        }
        observeChanges()
    }

    private fun observeChanges() {
        viewModel.posts.observe(this, Observer { showPosts(it) })
        viewModel.contentBlock.observe(this, Observer { showContentBlock(it) })
        viewModel.contentProgress.observe(this, Observer { showProgress(it) })
        viewModel.contentError.observe(this, Observer { showError(it) })
    }

    private fun showPosts(posts: List<Post>) {
        postsAdapter.submitList(posts)
    }

    private fun showContentBlock(show: Boolean) {
        postsRecyclerView.isVisible = show
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

        fun newInstance(userId: Long) = ProfilePostsFragment().apply {
            arguments = bundleOf(ARG_USER_ID to userId)
        }
    }
}