package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_forum.*
import kotlinx.android.synthetic.main.toolbar.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.navigationfactories.NavigationFactory
import javax.inject.Inject

@RegisterScreen(Screens.Forum::class)
class ForumFragment : BaseFragment() {
    @Inject lateinit var navigationFactory: NavigationFactory
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ForumViewModel

    override val layoutRes: Int = R.layout.fragment_forum

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.Forum>(this)
        val forumId = screen.forumId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ForumViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupPager()
        observeChanges()
    }

    private fun setupPager() {
        forumPager.adapter = ForumPagerAdapter(childFragmentManager, navigationFactory)
        forumTabLayout.setupWithViewPager(forumPager)
    }

    private fun observeChanges() {
        viewModel.forum.observe(this, Observer { showForumDetails(it) })
    }

    private fun showForumDetails(forum: Forum) {
        toolbar.title = forum.name

        forum.let {
            Glide.with(this@ForumFragment)
                .load(it.favicon.link)
                .into(avatarImageView)

            nameTextView.text = it.name
            descriptionTextView.text = it.description ?: it.url
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}