package com.akhbulatov.discusim.presentation.ui.main

import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.ui.global.base.FlowFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class MainFlowFragment : FlowFragment() {
    override fun getLaunchScreen(): SupportAppScreen = Screens.MainHost
}
