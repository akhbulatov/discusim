package com.akhbulatov.discusim.presentation.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import javax.inject.Inject

class AppActivity : DaggerAppCompatActivity() {
    @Inject lateinit var navigatorHolder: NavigatorHolder
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val navigator: Navigator by lazy { SupportAppNavigator(this, R.id.container) }
    private lateinit var viewModel: AppActivityViewModel

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[AppActivityViewModel::class.java]
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
