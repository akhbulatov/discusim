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

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _forum = MutableLiveData<Forum>()
    val forum: LiveData<Forum> get() = _forum

    fun setForumId(forumId: String) {
        this.forumId = forumId
        loadForumDetails()
    }

    private fun loadForumDetails() {
        subscriptions += forumInteractor.getForumDetails(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { _progress.value = true }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = { _forum.value = it },
                onError = { errorHandler.proceed(it) { msg -> _error.value = Pair(true, msg) } }
            )
    }

    override fun onBackPressed() = router.exit()
}