package com.akhbulatov.discusim.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val sessionInteractor: SessionInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun getAuthorizeUrl(): String = sessionInteractor.getAuthorizeUrl()

    /**
     * Происходит ли перенаправление на вход (получение токена)?
     *
     * @param url URL авторизации (authorize endpoint).
     * @return `true`, если произошло перенаправление на вход,
     * `false`, если получен код для выполнения входа (получения токена).
     */
    fun isRedirectToLogin(url: String): Boolean {
        val code = sessionInteractor.fetchAuthCode(url)
        code?.let {
            login(it)
            return true
        }
        return false
    }

    private fun login(code: String) {
        subscriptions += sessionInteractor.login(code)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _contentProgress.value = true }
            .doOnTerminate { _contentProgress.value = false }
            .subscribeBy(
                onComplete = { }, // TODO
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }
}