package com.akhbulatov.discusim.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.ui.global.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class AppActivity : DaggerAppCompatActivity() {
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val navigator: Navigator by lazy { SupportAppNavigator(this, R.id.container) }
    private val viewModel: AppViewModel by viewModels { viewModelFactory }

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_container)
        run { viewModel }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: viewModel.onBackPressed()
    }
}
