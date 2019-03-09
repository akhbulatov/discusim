package com.akhbulatov.discusim.presentation.global.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.discusim.R
import me.aartikov.alligator.NavigationContext
import me.aartikov.alligator.NavigationContextBinder
import javax.inject.Inject

abstract class FlowFragment : BaseFragment() {
    @Inject lateinit var navigationContextBinder: NavigationContextBinder

    private lateinit var navigationContext: NavigationContext

    private val currentChildFragment
        get() = childFragmentManager.findFragmentById(R.id.container) as? BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigationContext = NavigationContext.Builder(activity as AppCompatActivity)
            .containerId(R.id.container)
            .fragmentManager(childFragmentManager)
            .build()
    }

    override fun onResume() {
        super.onResume()
        navigationContextBinder.bind(navigationContext)
    }

    override fun onPause() {
        navigationContextBinder.unbind(activity as AppCompatActivity)
        super.onPause()
    }

    override fun onBackPressed() {
        currentChildFragment?.onBackPressed()
    }
}