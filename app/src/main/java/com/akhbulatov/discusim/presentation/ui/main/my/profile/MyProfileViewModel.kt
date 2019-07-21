package com.akhbulatov.discusim.presentation.ui.main.my.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.domain.user.UserInteractor
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MyProfileViewModel @Inject constructor(
    private val router: FlowRouter,
    private val userInteractor: UserInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _user = MutableLiveData<Pair<Boolean, UserDetails?>>()
    val user: LiveData<Pair<Boolean, UserDetails?>> get() = _user

    init {
        loadMyDetails()
    }

    fun loadMyDetails() {
        disposables += userInteractor.getMyDetails()
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