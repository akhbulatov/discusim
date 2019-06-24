package com.akhbulatov.discusim.presentation.ui.main

import android.os.Bundle
import android.view.View
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import kotlinx.android.synthetic.main.fragment_main_flow.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowFragment : FlowFragment() {
    override val layoutRes: Int = R.layout.fragment_main_flow

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavView.setOnNavigationItemSelectedListener { item ->
            val tabScreen = when (item.itemId) {
                R.id.menu_bottom_nav_user_activity -> userActivityTabScreen
                R.id.menu_bottom_nav_trends -> trendsTabScreen
                R.id.menu_bottom_nav_forums -> forumsTabScreen
                else -> profileTabScreen
            }
            switchTab(tabScreen)
            true
        }


        val tabScreen = when (currentTabFragment?.tag) {
            trendsTabScreen.screenKey -> trendsTabScreen
            forumsTabScreen.screenKey -> forumsTabScreen
            profileTabScreen.screenKey -> profileTabScreen
            else -> userActivityTabScreen // Первый таб, открываемый по умолчанию
        }
        switchTab(tabScreen)
    }

    private fun switchTab(tabScreen: SupportAppScreen) {
        val currentFragment = currentTabFragment
        val newFragment = childFragmentManager.findFragmentByTag(tabScreen.screenKey)

        if (currentFragment != null && newFragment != null && currentFragment == newFragment) {
            return // Не переключает таб, т.к. нажатие выполнено на текущем табе
        }

        childFragmentManager.beginTransaction().apply {
            if (newFragment == null) {
                add(R.id.container, tabScreen.fragment, tabScreen.screenKey)
            }

            currentFragment?.let { hide(it) }
            newFragment?.let { show(it) }
        }.commitNow()
    }

    companion object {
        private val userActivityTabScreen = Screens.UserActivity()
        private val trendsTabScreen = Screens.TrendsContainer
        private val forumsTabScreen = Screens.Forums(178987138) // TODO
        private val profileTabScreen = Screens.Profile(178987138) // TODO
    }
}