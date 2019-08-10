package com.akhbulatov.discusim.presentation.ui.forum.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.forum.ForumInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.forum.ForumDetails
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
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

    private val _forum = MutableLiveData<Pair<Boolean, ForumDetails?>>()
    val forum: LiveData<Pair<Boolean, ForumDetails?>> get() = _forum

    private val _followProgress = MutableLiveData<Boolean>()
    val followProgress: LiveData<Boolean> get() = _followProgress

    private val _followError = MutableLiveData<String>()
    val followError: LiveData<String> get() = _followError

    private val _following = MutableLiveData<ForumDetails>()
    val following: LiveData<ForumDetails> get() = _following

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

    fun onFollowClicked(following: Boolean, forum: ForumDetails) {
        if (!following) followForum(forum) else unfollowForum(forum)
    }

    private fun followForum(forum: ForumDetails) {
        disposables += forumInteractor.followForum(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _followProgress.value = true }
            .doAfterTerminate { _followProgress.value = false }
            .subscribeBy(
                onComplete = { _following.value = updatedForum(true, forum) },
                onError = { errorHandler.proceed(it) { msg -> _followError.value = msg } }
            )
    }

    private fun unfollowForum(forum: ForumDetails) {
        disposables += forumInteractor.unfollowForum(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _followProgress.value = true }
            .doAfterTerminate { _followProgress.value = false }
            .subscribeBy(
                onComplete = { _following.value = updatedForum(false, forum) },
                onError = { errorHandler.proceed(it) { msg -> _followError.value = msg } }
            )
    }

    private fun updatedForum(following: Boolean, forum: ForumDetails): ForumDetails {
        val oldFollowers = forum.numFollowers
        val newFollowers = if (following) oldFollowers + 1 else oldFollowers - 1
        return forum.copy(following = following, numFollowers = newFollowers)
    }

    override fun onBackPressed() = router.exit()
}
