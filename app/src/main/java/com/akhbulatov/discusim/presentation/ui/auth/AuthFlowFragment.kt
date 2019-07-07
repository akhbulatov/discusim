package com.akhbulatov.discusim.presentation.ui.auth

import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AuthFlowFragment : FlowFragment() {
    override fun getLaunchScreen(): SupportAppScreen = Screens.Auth
}