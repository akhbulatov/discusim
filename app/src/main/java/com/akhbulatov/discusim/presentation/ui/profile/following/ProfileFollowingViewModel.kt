package com.akhbulatov.discusim.presentation.ui.profile.following

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.profile.following.ProfileFollowingInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ProfileFollowingViewModel @Inject constructor(
    private val interactor: ProfileFollowingInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _following = MutableLiveData<List<Any>>()
    val following: LiveData<List<Any>> get() = _following

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setInitialData(userId: Long, followingType: ProfileFollowingType) {
        loadFollowing(userId, followingType)
    }

    private fun loadFollowing(userId: Long, followingType: ProfileFollowingType) {
        val followingRequest = when (followingType) {
            ProfileFollowingType.USER -> interactor.getFollowingUsers(userId)
            ProfileFollowingType.FORUM -> interactor.getFollowingForums(userId)
        }

        subscriptions += followingRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _following.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }
}