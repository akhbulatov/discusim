package com.akhbulatov.discusim.presentation.ui.forum.channel

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.forum.ForumSharedViewModel
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.loadImage
import kotlinx.android.synthetic.main.fragment_channel_host.*
import org.jetbrains.anko.support.v4.share
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ChannelHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_channel_host

    private val forumSharedViewModel: ForumSharedViewModel by viewModels()

    private lateinit var forumDetailsTabScreen: SupportAppScreen
    private lateinit var forumDiscussionsTabScreen: SupportAppScreen

    private var sharedForum: ForumDetails? = null

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        forumDetailsTabScreen = Screens.ChannelDetailsHost(forumId)
        forumDiscussionsTabScreen = Screens.ChannelDiscussionsHost(forumId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        forumBottomNavView.setOnNavigationItemSelectedListener { item ->
            val tabScreen = when (item.itemId) {
                R.id.menu_bottom_nav_channel_details -> forumDetailsTabScreen
                R.id.menu_bottom_nav_channel_discussions -> forumDiscussionsTabScreen
                else -> throw IllegalArgumentException()
            }
            switchTab(tabScreen)
            true
        }
        observeUiChanges()

        val tabScreen = when (currentTabFragment?.tag) {
            forumDiscussionsTabScreen.screenKey -> forumDiscussionsTabScreen
            else -> forumDetailsTabScreen // Первый таб, открываемый по умолчанию
        }
        switchTab(tabScreen)
    }

    private fun setupToolbar() {
        with(toolbar) {
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                sharedForum?.let { share(it.webUrl) }
                true
            }
        }
    }

    private fun observeUiChanges() {
        forumSharedViewModel.forum.observe(this, Observer { forum ->
            this.sharedForum = forum

            collapsingToolbar.title = forum.name
            forum.channel?.let {
                if (it.bannerUrl != null) {
                    bannerImageView.loadImage(context, it.bannerUrl)
                } else {
                    bannerImageView.setBackgroundColor(it.bannerColorHex)
                }
            }
        })
    }

    private fun switchTab(tabScreen: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tabScreen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return // Не переключает таб, т.к. нажатие выполнено на текущем табе
        }

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                add(R.id.forumContainer, tabScreen.fragment, tabScreen.screenKey)
            }

            currentFragment?.let { hide(it) }
            newFragment?.let { show(it) }
        }.commitNow()
    }

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }

    companion object {
        private const val ARG_FORUM_ID = "forum_id"

        fun newInstance(forumId: String) = ChannelHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}
