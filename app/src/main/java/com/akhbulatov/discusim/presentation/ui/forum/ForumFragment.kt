package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class ForumFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ForumViewModel

    override val layoutRes: Int = R.layout.fragment_forum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        observeChanges()
    }

    private fun setupPager() {
        forumPager.adapter = ForumPagerAdapter(childFragmentManager)
        forumTabLayout.setupWithViewPager(forumPager)
    }

    private fun observeChanges() {
        viewModel.forum.observe(this, Observer { showForumDetails(it) })
    }

    private fun showForumDetails(forum: Forum) {
        toolbar.title = forum.name

        forum.let {
            Glide.with(this@ForumFragment)
                .load(it.faviconUrl)
                .into(avatarImageView)

            nameTextView.text = it.name
            descriptionTextView.text = it.description ?: it.url
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ForumFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}