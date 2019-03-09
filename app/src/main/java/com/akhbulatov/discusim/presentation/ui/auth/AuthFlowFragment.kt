package com.akhbulatov.discusim.presentation.ui.auth

import android.os.Bundle
import com.akhbulatov.discusim.R
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.FlowFragment
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.annotations.RegisterScreen
import javax.inject.Inject

@RegisterScreen(Screens.AuthFlow::class)
class AuthFlowFragment : FlowFragment() {
    @Inject lateinit var navigator: Navigator

    override val layoutRes: Int = R.layout.container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.reset(Screens.Auth)
    }
}