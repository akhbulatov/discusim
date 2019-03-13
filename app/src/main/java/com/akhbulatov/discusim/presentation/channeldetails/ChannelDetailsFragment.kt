package com.akhbulatov.discusim.presentation.channeldetails

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
import kotlinx.android.synthetic.main.fragment_channel_details.*
import kotlinx.android.synthetic.main.toolbar.*
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.ChannelDetails::class)
class ChannelDetailsFragment : BaseFragment() {
    //    @Inject lateinit var navigationFactory: NavigationFactory
    @Inject lateinit var screenResolver: ScreenResolver
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChannelDetailsViewModel

    override val layoutRes: Int = R.layout.fragment_channel_details

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val screen = screenResolver.getScreen<Screens.ChannelDetails>(this)
        val forumId = screen.forumId

        viewModel = ViewModelProviders.of(this, viewModelFactory)[ChannelDetailsViewModel::class.java]
        viewModel.setForumId(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
//        setupPager()
        observeChanges()
    }

    private fun setupToolbar() {
        toolbar.run {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { onBackPressed() }
        }
    }

//    private fun setupPager() {
//        channelDetailsPager.adapter = ChannelDetailsPagerAdapter(childFragmentManager, navigationFactory)
//        channelDetailsTabLayout.setupWithViewPager(channelDetailsPager)
//    }

    private fun observeChanges() {
        viewModel.forum.observe(this, Observer { showChannelDetails(it) })
    }

    private fun showChannelDetails(forum: Forum) {
        forum.let {
            toolbar.title = it.name

            Glide.with(this@ChannelDetailsFragment)
                .load(it.faviconUrl)
                .into(faviconImageView)

            nameTextView.text = it.name
            descriptionTextView.text = it.description
            it.numThreads?.let { threads ->
                val count = resources.getQuantityString(R.plurals.channel_details_num_threads, threads, threads)
                numThreadsTextView.text = count
            }
            it.numFollowers?.let { followers ->
                val count = resources.getQuantityString(R.plurals.channel_details_num_followers, followers, followers)
                numFollowersTextView.text = count
            }
        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()
}