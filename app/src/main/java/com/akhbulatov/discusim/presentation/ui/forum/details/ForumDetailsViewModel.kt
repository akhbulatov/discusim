package com.akhbulatov.discusim.presentation.ui.forum.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.forum.ForumInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ForumDetailsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val forumInteractor: ForumInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private lateinit var forumId: String

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<Pair<Boolean, String?>>()
    val emptyError: LiveData<Pair<Boolean, String?>> get() = _emptyError

    private val _forum = MutableLiveData<Pair<Boolean, Forum?>>()
    val forum: LiveData<Pair<Boolean, Forum?>> get() = _forum

    private val _followProgress = MutableLiveData<Boolean>()
    val followProgress: LiveData<Boolean> get() = _followProgress

    private val _followError = MutableLiveData<String>()
    val followError: LiveData<String> get() = _followError

    private val _follow = MutableLiveData<Unit>()
    val follow: LiveData<Unit> get() = _follow

    private val _unfollow = MutableLiveData<Unit>()
    val unfollow: LiveData<Unit> get() = _unfollow

    fun setForumId(forumId: String) {
        this.forumId = forumId
        loadForumDetails()
    }

    fun loadForumDetails() {
        disposables += forumInteractor.getForumDetails(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _forum.value = Pair(false, null)
                _emptyError.value = Pair(false, null)
                _emptyProgress.value = true
            }
            .doAfterTerminate { _emptyProgress.value = false }
            .subscribeBy(
                onSuccess = { _forum.value = Pair(true, it) },
                onError = { errorHandler.proceed(it) { msg -> _emptyError.value = Pair(true, msg) } }
            )
    }

    fun onFollowClicked(following: Boolean) {
        if (!following) followForum() else unfollowForum()
    }

    private fun followForum() {
        disposables += forumInteractor.followForum(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _followProgress.value = true }
            .doAfterTerminate { _followProgress.value = false }
            .subscribeBy(
                onComplete = { _follow.value = Unit },
                onError = { errorHandler.proceed(it) { msg -> _followError.value = msg } }
            )
    }

    private fun unfollowForum() {
        disposables += forumInteractor.unfollowForum(forumId)
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