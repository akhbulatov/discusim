package com.akhbulatov.discusim.presentation.ui.global.base

import android.os.Bundle
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.di.Flow
import com.akhbulatov.discusim.presentation.global.FlowRouter
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.android.support.SupportAppScreen
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {
    override val layoutRes: Int = R.layout.layout_container

    @Inject lateinit var router: FlowRouter
    @Inject @field:Flow lateinit var navigatorHolder: NavigatorHolder

    private val navigator: Navigator by lazy {
        object : SupportAppNavigator(activity, childFragmentManager, R.id.container) {
            // Переопределяется для возврата на предыдущий flow-фрагмент
            override fun activityBack() {
                router.finishFlow()
            }
        }
    }

    private val currentFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    abstract fun getLaunchScreen(): SupportAppScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(getLaunchScreen())
    }

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