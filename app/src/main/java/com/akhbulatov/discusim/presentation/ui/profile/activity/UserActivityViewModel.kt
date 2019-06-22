package com.akhbulatov.discusim.presentation.ui.profile.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.activity.ActivityInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Action
import com.akhbulatov.discusim.domain.session.SessionInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class UserActivityViewModel @Inject constructor(
    private val router: FlowRouter,
    private val sessionInteractor: SessionInteractor,
    private val activityInteractor: ActivityInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _actions = MutableLiveData<List<Action>>()
    val actions: LiveData<List<Action>> get() = _actions

    private val _content = MutableLiveData<Boolean>()
    val content: LiveData<Boolean> get() = _content

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun setUserId(userId: Long?) {
        loadActivity(userId ?: sessionInteractor.getUserId())
    }

    private fun loadActivity(userId: Long) {
        subscriptions += activityInteractor.getUserActivity(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _content.value = false
                _progress.value = true
            }
            .doAfterTerminate { _progress.value = false }
            .subscribeBy(
                onSuccess = {
                    _content.value = true
                    _actions.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _error.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}