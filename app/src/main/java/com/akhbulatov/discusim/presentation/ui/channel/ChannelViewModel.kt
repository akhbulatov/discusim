package com.akhbulatov.discusim.presentation.ui.channel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.channel.ChannelInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ChannelViewModel @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ChannelInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _forum = MutableLiveData<Forum>()
    val forum: LiveData<Forum> get() = _forum

    fun setForumId(forumId: String) {
        if (!forumId.startsWith("channel-")) {
            throw IllegalArgumentException("A forum with id: $forumId is not a channel.")
        }
        loadChannelDetails(forumId)
    }

    private fun loadChannelDetails(forumId: String) {
        subscriptions += interactor.getChannelDetails(forumId)
            .observeOn(schedulers.ui())
            .doOnSubscribe { } // TODO
            .doAfterTerminate { } // TODO
            .subscribeBy(
                onSuccess = { _forum.value = it },
                onError = { errorHandler.proceed(it) {} } // TODO
            )
    }

    fun onAboutClicked(forumId: String) = router.navigateTo(Screens.ChannelDetails(forumId))

    override fun onBackPressed() = router.exit()
}