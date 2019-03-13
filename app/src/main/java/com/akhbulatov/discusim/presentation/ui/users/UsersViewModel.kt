package com.akhbulatov.discusim.presentation.ui.users

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.User
import com.akhbulatov.discusim.domain.users.UsersInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UsersViewModel @Inject constructor(
    private val interactor: UsersInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> get() = _users

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setInitialData(userId: String, userType: UserType) {
        loadUsers(userId, userType)
    }

    private fun loadUsers(itemId: String, userType: UserType) {
        val usersRequest = when (userType) {
            UserType.FOLLOWING -> interactor.getFollowingUsers(itemId.toLong())
            UserType.FOLLOWER -> interactor.getFollowers(itemId.toLong())
            UserType.TOP_COMMENTER -> interactor.getTopCommenters(itemId)
            UserType.MODERATOR -> interactor.getModerators(itemId)
        }

        subscriptions += usersRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _users.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }
}