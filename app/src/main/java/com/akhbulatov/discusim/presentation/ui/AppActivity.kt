package com.akhbulatov.discusim.presentation.ui

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.base.BaseFragment
import dagger.android.support.DaggerAppCompatActivity
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import javax.inject.Inject

class AppActivity : DaggerAppCompatActivity() {
    @Inject lateinit var navigationContextBinder: NavigationContextBinder
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var navigationContext: NavigationContext
    private lateinit var viewModel: AppActivityViewModel

    private val currentFragment
        get() = supportFragmentManager.findFragmentById(R.id.container) as BaseFragment?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.container)

        viewModel = ViewModelProviders.of(this, viewModelFactory)[AppActivityViewModel::class.java]
        navigationContext = NavigationContext.Builder(this)
            .containerId(R.id.container)
            .build()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        navigationContextBinder.unbind(this)
        super.onPause()
    }

    override fun onBackPressed() {
        currentFragment?.onBackPressed() ?: super.onBackPressed()
    }
}
