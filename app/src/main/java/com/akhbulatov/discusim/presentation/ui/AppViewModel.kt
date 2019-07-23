package com.akhbulatov.discusim.presentation.ui

import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AppViewModel @Inject constructor(
    private val router: Router,
    sessionInteractor: SessionInteractor
) : BaseViewModel() {

    init {
        val screen = if (sessionInteractor.isLoggedIn()) Screens.MainFlow else Screens.AuthFlow
        router.newRootScreen(screen)
    }

    override fun onBackPressed() = router.exit()
}
