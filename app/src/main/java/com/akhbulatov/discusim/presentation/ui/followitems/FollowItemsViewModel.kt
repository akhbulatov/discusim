package com.akhbulatov.discusim.presentation.ui.followitems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.followitems.FollowItemsInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class FollowItemsViewModel @Inject constructor(
    private val interactor: FollowItemsInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _followItems = MutableLiveData<List<Any>>()
    val followItems: LiveData<List<Any>> get() = _followItems

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setInitialData(itemId: String, followType: FollowItemType) {
        loadFollowing(itemId, followType)
    }

    private fun loadFollowing(itemId: String, followType: FollowItemType) {
        val followItemsRequest = when (followType) {
            FollowItemType.FOLLOWING_USER -> interactor.getFollowingUsers(itemId.toLong())
            FollowItemType.FOLLOWING_FORUM -> interactor.getFollowingForums(itemId.toLong())
            FollowItemType.FOLLOWER -> interactor.getFollowers(itemId.toLong())
            FollowItemType.TOP_COMMENTERS -> interactor.getTopCommenters(itemId)
        }

        subscriptions += followItemsRequest
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _followItems.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }
}