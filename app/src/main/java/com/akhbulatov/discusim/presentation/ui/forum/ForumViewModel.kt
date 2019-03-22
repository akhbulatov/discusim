package com.akhbulatov.discusim.presentation.ui.forum

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

class ForumViewModel @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ForumInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _forum = MutableLiveData<Forum>()
    val forum: LiveData<Forum> get() = _forum

    fun setForumId(forumId: String) {
        loadForumDetails(forumId)
    }

    private fun loadForumDetails(forumId: String) {
        subscriptions += interactor.getForumDetails(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { } // TODO
            .doAfterTerminate { } // TODO
            .subscribeBy(
                onSuccess = { _forum.value = it },
                onError = { errorHandler.proceed(it) {} } // TODO
            )
    }

    override fun onBackPressed() = router.exit()
}