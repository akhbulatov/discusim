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

    private var userId: Long = -1

    private val _actions = MutableLiveData<List<Action>>()
    val actions: LiveData<List<Action>> get() = _actions

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> get() = _progress

    private val _refreshProgress = MutableLiveData<Boolean>()
    val refreshProgress: LiveData<Boolean> get() = _refreshProgress

    private val _error = MutableLiveData<Pair<Boolean, String?>>()
    val error: LiveData<Pair<Boolean, String?>> get() = _error

    private val _refreshError = MutableLiveData<String>()
    val refreshError: LiveData<String> get() = _refreshError

    fun setUserId(userId: Long?) {
        this.userId = userId ?: sessionInteractor.getUserId()
        loadActivity()
    }

    private fun loadActivity(refresh: Boolean = false) {
        subscriptions += activityInteractor.getUserActivity(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                if (!refresh) {
                    _progress.value = true
                } else {
                    _refreshProgress.value = true
                }
            }
            .doAfterTerminate {
                if (!refresh) {
                    _progress.value = false
                } else {
                    _refreshProgress.value = false
                }
            }
            .subscribeBy(
                onSuccess = {
                    if (_error.value?.first != false) {
                        _error.value = Pair(false, null)
                    }
                    _actions.value = it
                },
                onError = {
                    errorHandler.proceed(it) { msg ->
                        if (!refresh) {
                            _error.value = Pair(true, msg)
                        } else {
                            _refreshError.value = msg
                        }
                    }
                }
            )
    }

    fun refreshActivity() = loadActivity(refresh = true)

    override fun onBackPressed() = router.exit()
}