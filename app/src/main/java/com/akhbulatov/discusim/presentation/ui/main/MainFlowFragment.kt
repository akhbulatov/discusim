package com.akhbulatov.discusim.presentation.ui.main

import android.os.Bundle
import android.view.View
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.global.base.FlowFragment
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowFragment : FlowFragment() {
    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override val layoutRes: Int = R.layout.fragment_main_flow

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavBar.setOnNavigationItemSelectedListener {
            switchTab(
                when (it.itemId) {
                    R.id.menu_bottom_nav_profile -> profileTab
                    R.id.menu_bottom_nav_forums -> forumsTab
                    else -> throw IllegalArgumentException("") // TODO
                }
            )
            true
        }

        switchTab(
            when (currentTabFragment?.tag) {
                profileTab.screenKey -> profileTab
                forumsTab.screenKey -> forumsTab
                else -> profileTab
            }
        )
    }

    private fun switchTab(tabScreen: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tabScreen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return
        }

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                add(R.id.container, tabScreen.fragment, tabScreen.screenKey)
            }

            currentFragment?.let {
                hide(it)
                it.userVisibleHint = false
            }

            newFragment?.let {
                show(it)
                it.userVisibleHint = true
            }
        }.commitNow()
    }

    companion object {
        private val profileTab = Screens.Profile(178987138) // TODO
        private val forumsTab = Screens.Forums(178987138) // TODO
    }
}