package com.akhbulatov.discusim.presentation.channel

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
import kotlinx.android.synthetic.main.fragment_channel.*
import kotlinx.android.synthetic.main.toolbar.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import me.aartikov.alligator.navigationfactories.NavigationFactory
import javax.inject.Inject

@RegisterScreen(Screens.Channel::class)
class ChannelFragment : BaseFragment() {
    @Inject lateinit var navigationFactory: NavigationFactory
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChannelViewModel

    override val layoutRes: Int = R.layout.fragment_channel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.Channel>(this)
        val forumId = screen.forumId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ChannelViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutButton.setOnClickListener { viewModel.onAboutClicked("channel-gifs") } // TODO
        setupPager()
        observeChanges()
    }

    private fun setupPager() {
        channelPager.adapter = ChannelPagerAdapter(childFragmentManager, navigationFactory)
        channelTabLayout.setupWithViewPager(channelPager)
    }

    private fun observeChanges() {
        viewModel.forum.observe(this, Observer { showChannel(it) })
    }

    private fun showChannel(forum: Forum) {
        forum.channel?.let {
            toolbar.title = forum.name

            Glide.with(this@ChannelFragment)
                .load(it.backgroundUrl)
                .into(coverImageView)

            Glide.with(this@ChannelFragment)
                .load((it.logoUrl))
                .into(logoImageView)
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}