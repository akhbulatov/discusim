package com.akhbulatov.discusim.presentation.ui.main

import android.os.Bundle
import android.view.View
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_main_host.*
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainHostFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.fragment_main_host

    private val currentTabFragment: BaseFragment?
        get() = childFragmentManager.fragments.firstOrNull { !it.isHidden } as? BaseFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainBottomNavView.setOnNavigationItemSelectedListener { item ->
            val tabScreen = when (item.itemId) {
                R.id.menu_bottom_nav_my_activity -> Screens.MyActivity
                R.id.menu_bottom_nav_my_forums -> Screens.MyForums
                R.id.menu_bottom_nav_my_profile -> Screens.MyProfile
                else -> throw IllegalArgumentException()
            }
            switchTab(tabScreen)
            true
        }

        val tabScreen = when (currentTabFragment?.tag) {
            Screens.MyForums.screenKey -> Screens.MyForums
            Screens.MyProfile.screenKey -> Screens.MyProfile
            else -> Screens.MyActivity // Первый таб, открываемый по умолчанию
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
                add(R.id.mainContainer, tabScreen.fragment, tabScreen.screenKey)
            }

            currentFragment?.let { hide(it) }
            newFragment?.let { show(it) }
        }.commitNow()
    }

    override fun onBackPressed() {
        currentTabFragment?.onBackPressed()
    }
}
