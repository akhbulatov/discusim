package com.akhbulatov.discusim.presentation.ui.forums

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.forums.ForumsInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Forum
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ForumsViewModel @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ForumsInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _forums = MutableLiveData<List<Forum>>()
    val forums: LiveData<List<Forum>> get() = _forums

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setUserId(userId: Long) {
        loadForums(userId)
    }

    private fun loadForums(userId: Long) {
        subscriptions += interactor.getFollowingForums(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _forums.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}