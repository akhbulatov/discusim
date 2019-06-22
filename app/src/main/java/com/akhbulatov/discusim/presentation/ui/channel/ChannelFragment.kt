package com.akhbulatov.discusim.presentation.ui.channel

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ViewModelFactory
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_channel.*
import javax.inject.Inject

class ChannelFragment : BaseFragment() {
    @Inject lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ChannelViewModel

    override val layoutRes: Int = R.layout.fragment_channel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))

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
        channelPager.adapter = ChannelPagerAdapter(context!!, childFragmentManager)
        channelTabLayout.setupWithViewPager(channelPager)
    }

    private fun observeChanges() {
        viewModel.forum.observe(this, Observer { showChannel(it) })
    }

    private fun showChannel(forum: Forum) {
//        forum.channel?.let {
//            toolbar.title = forum.name
//
//            Glide.with(this@ChannelFragment)
//                .load(it.backgroundUrl)
//                .into(coverImageView)
//
//            Glide.with(this@ChannelFragment)
//                .load((it.logoUrl))
//                .into(logoImageView)
//        }
    }

    override fun onBackPressed() = viewModel.onBackPressed()

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ChannelFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}