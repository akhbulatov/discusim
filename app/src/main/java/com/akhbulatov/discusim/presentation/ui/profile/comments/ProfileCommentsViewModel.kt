package com.akhbulatov.discusim.presentation.ui.profile.comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.Comment
import com.akhbulatov.discusim.domain.profile.comments.ProfileCommentsInteractor
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.base.BaseViewModel
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class ProfileCommentsViewModel @Inject constructor(
    private val interactor: ProfileCommentsInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _comments = MutableLiveData<List<Comment>>()
    val comments: LiveData<List<Comment>> get() = _comments

    private val _contentBlock = MutableLiveData<Boolean>()
    val contentBlock: LiveData<Boolean> get() = _contentBlock

    private val _contentProgress = MutableLiveData<Boolean>()
    val contentProgress: LiveData<Boolean> get() = _contentProgress

    private val _contentError = MutableLiveData<String>()
    val contentError: LiveData<String> get() = _contentError

    fun setUserId(userId: Long) {
        loadComments(userId)
    }

    private fun loadComments(userId: Long) {
        subscriptions += interactor.getComments(userId)
            .observeOn(schedulers.ui())
            .doOnSubscribe {
                _contentBlock.value = false
                _contentProgress.value = true
            }
            .doAfterTerminate { _contentProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    _contentBlock.value = true
                    _comments.value = it
                },
                onError = { errorHandler.proceed(it) { msg -> _contentError.value = msg } }
            )
    }
}