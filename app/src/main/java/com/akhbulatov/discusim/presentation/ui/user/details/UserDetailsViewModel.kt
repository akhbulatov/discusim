package com.akhbulatov.discusim.presentation.ui.user.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.user.UserInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val userInteractor: UserInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private var userId: Long = 0

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _user = MutableLiveData<Pair<Boolean, User?>>()
    val user: LiveData<Pair<Boolean, User?>> get() = _user

    fun setUserId(userId: Long) {
        this.userId = userId
        loadUserDetails()
    }

    fun loadUserDetails() {
        disposables += userInteractor.getUserDetails(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _user.value = Pair(false, null)
                _error.value = Pair(false, null)
                _progress.value = true
            }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = { _user.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _error.value = Pair(true, msg) } }
            )
    }

    override fun onBackPressed() = router.exit()
}