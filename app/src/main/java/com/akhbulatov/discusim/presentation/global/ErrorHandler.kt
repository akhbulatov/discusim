package com.akhbulatov.discusim.presentation.global

import com.akhbulatov.discusim.domain.global.ResourceManager
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.ui.global.utils.userMessage
import retrofit2.HttpException
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    private val resourceManager: ResourceManager
) {

    fun proceed(error: Throwable, messageListener: (String) -> Unit) {
        Timber.e(error)
        when (error) {
            is HttpException -> when (error.code()) {
                401 -> logout() // Токен истек
                else -> messageListener(error.userMessage(resourceManager))
            }
            else -> messageListener(error.userMessage(resourceManager))
        }
    }

    private fun logout() {
        sessionInteractor.logout()
    }
}