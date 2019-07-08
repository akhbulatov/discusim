package com.akhbulatov.discusim.presentation.ui.forum

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.utils.loadImage
import kotlinx.android.synthetic.main.fragment_forum_host.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class ForumHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_forum_host

    private lateinit var forumSharedViewModel: ForumSharedViewModel

    private lateinit var forumDetailsTabScreen: SupportAppScreen
    private lateinit var forumDiscussionsTabScreen: SupportAppScreen

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val forumId = requireNotNull(arguments?.getString(ARG_FORUM_ID))
        forumDetailsTabScreen = Screens.ForumDetailsHost(forumId)
        forumDiscussionsTabScreen = Screens.ForumDiscussionsHost(forumId)

        forumSharedViewModel = ViewModelProviders.of(this)[ForumSharedViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setNavigationOnClickListener { onBackPressed() }
        forumBottomNavView.setOnNavigationItemSelectedListener { item ->
            val tabScreen = when (item.itemId) {
                R.id.menu_bottom_nav_forum_details -> forumDetailsTabScreen
                R.id.menu_bottom_nav_forum_discussions -> forumDiscussionsTabScreen
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

    private fun observeUiChanges() {
        forumSharedViewModel.forum.observe(this, Observer { forum ->
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

        fun newInstance(forumId: String) = ForumHostFragment().apply {
            arguments = bundleOf(ARG_FORUM_ID to forumId)
        }
    }
}