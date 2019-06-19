package com.akhbulatov.discusim.presentation.ui.auth

import android.os.Bundle
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment

class AuthFlowFragment : FlowFragment() {
    override val layoutRes: Int = R.layout.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(Screens.Auth)
    }
}