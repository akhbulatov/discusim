package com.akhbulatov.discusim.presentation.ui.user.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.user.UserDetails
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.domain.user.UserInteractor
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UserDetailsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val userInteractor: UserInteractor,
    private val sessionInteractor: SessionInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private var userId: Long = 0

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _user = MutableLiveData<Pair<Boolean, UserDetails?>>()
    val user: LiveData<Pair<Boolean, UserDetails?>> get() = _user

    private val _followProgress = MutableLiveData<Boolean>()
    val followProgress: LiveData<Boolean> get() = _followProgress

    private val _followError = MutableLiveData<String>()
    val followError: LiveData<String> get() = _followError

    private val _follow = MutableLiveData<Unit>()
    val follow: LiveData<Unit> get() = _follow

    private val _unfollow = MutableLiveData<Unit>()
    val unfollow: LiveData<Unit> get() = _unfollow

    fun setUserId(userId: Long) {
        this.userId = userId
        loadUserDetails()
    }

    fun loadUserDetails() {
        disposables += userInteractor.getUserDetails(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _user.value = Pair(false, null)
                _emptyError.value = Pair(false, null)
                _emptyProgress.value = true
            }
            .doAfterTerminate { _emptyProgress.value = false }
            .subscribeBy(
                onSuccess = { _user.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _emptyError.value = Pair(true, msg) } }
            )
    }

    fun isLoggedUser(): Boolean = sessionInteractor.isLoggedIn(userId)

    fun onFollowClicked(following: Boolean) {
        if (!following) followUser() else unfollowUser()
    }

    private fun followUser() {
        disposables += userInteractor.followUser(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _followProgress.value = true }
            .doAfterTerminate { _followProgress.value = false }
            .subscribeBy(
                onComplete = { _follow.value = Unit },
                onError = { errorHandler.proceed(it) { msg -> _followError.value = msg } }
            )
    }

    private fun unfollowUser() {
        disposables += userInteractor.unfollowUser(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _followProgress.value = true }
            .doAfterTerminate { _followProgress.value = false }
            .subscribeBy(
                onComplete = { _unfollow.value = Unit },
                onError = { errorHandler.proceed(it) { msg -> _followError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}
