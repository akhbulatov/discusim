package com.akhbulatov.discusim.presentation.global

import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.ui.global.utils.userMessage
import retrofit2.HttpException
import ru.terrakok.cicerone.Router
import timber.log.Timber

class ErrorHandler(
    private val router: Router,
    private val sessionInteractor: SessionInteractor,
    private val resourceManager: ResourceManager
) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit) {
        Timber.e(error)
        when (error) {
            // Сетевая ошибка
            is HttpException -> when (error.code()) {
                401 -> logout() // Токен истек, сессия юзера завершена
                else -> messageListener(error.userMessage(resourceManager))
            }
            else -> messageListener(error.userMessage(resourceManager))
        }
    }

    private fun logout() {
        sessionInteractor.logout()
        router.newRootScreen(Screens.AuthFlow)
    }
}