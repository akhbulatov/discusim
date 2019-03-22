package com.akhbulatov.discusim.presentation.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.UserDetails
import com.akhbulatov.discusim.domain.profile.ProfileInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val router: FlowRouter,
    private val profileInteractor: ProfileInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _profile = MutableLiveData<UserDetails>()
    val profile: LiveData<UserDetails> get() = _profile

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setUserId(userId: Long) {
        loadProfile(userId)
    }

    private fun loadProfile(userId: Long) {
        subscriptions += profileInteractor.getProfile(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _profile.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}