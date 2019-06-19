package com.akhbulatov.discusim.presentation.ui.global.base

import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.di.Flow
import com.akhbulatov.discusim.presentation.global.FlowRouter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {
    @Inject lateinit var router: FlowRouter
    @Inject @field:Flow lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, R.id.container) {
            override fun activityBack() {
                router.finishFlow()
            }
        }
    }

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed()
    }
}