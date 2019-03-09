package com.akhbulatov.discusim.presentation.ui

import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import me.aartikov.alligator.Navigator
import javax.inject.Inject

class AppActivityViewModel @Inject constructor(
    navigator: Navigator,
    sessionInteractor: SessionInteractor
) : BaseViewModel() {

    init {
        val screen = if (sessionInteractor.isLoggedIn()) Screens.MainFlow else Screens.AuthFlow
        navigator.reset(screen)
    }
}