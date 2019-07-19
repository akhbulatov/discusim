package com.akhbulatov.discusim.presentation.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.SingleLiveEvent
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AuthViewModel @Inject constructor(
    private val router: FlowRouter,
    private val sessionInteractor: SessionInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _errorMessage = SingleLiveEvent<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

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
        disposables += sessionInteractor.login(code)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _progress.value = true }
            .doOnTerminate { _progress.value = false }
            .subscribeBy(
                onComplete = { router.newRootFlow(Screens.MainFlow) },
                onError = { errorHandler.proceed(it) { msg -> _errorMessage.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}