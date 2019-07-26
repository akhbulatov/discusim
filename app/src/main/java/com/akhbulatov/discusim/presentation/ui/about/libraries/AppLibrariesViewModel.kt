package com.akhbulatov.discusim.presentation.ui.about.libraries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppLibrary
import com.akhbulatov.discusim.domain.library.LibraryInteractor
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.ErrorHandler
import com.akhbulatov.discusim.presentation.global.FlowRouter
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class AppLibrariesViewModel @Inject constructor(
    private val router: FlowRouter,
    private val libraryInteractor: LibraryInteractor,
    private val schedulers: SchedulersProvider,
    private val errorHandler: ErrorHandler
) : BaseViewModel() {

    private val _emptyProgress = MutableLiveData<Boolean>()
    val emptyProgress: LiveData<Boolean> get() = _emptyProgress

    private val _emptyError = MutableLiveData<String>()
    val emptyError: LiveData<String> get() = _emptyError

    private val _emptyData = MutableLiveData<Boolean>()
    val emptyData: LiveData<Boolean> get() = _emptyData

    private val _appLibraries = MutableLiveData<List<AppLibrary>>()
    val appLibraries: LiveData<List<AppLibrary>> get() = _appLibraries

    init {
        loadAppLibraries()
    }

    private fun loadAppLibraries() {
        disposables += libraryInteractor.getAppLibraries()
            .observeOn(schedulers.ui())
            .doOnSubscribe { _emptyProgress.value = true }
            .doAfterTerminate { _emptyProgress.value = false }
            .subscribeBy(
                onSuccess = {
                    if (it.isNotEmpty()) {
                        _appLibraries.value = it
                    } else {
                        _emptyData.value = true
                    }
                },
                onError = { errorHandler.proceed(it) { msg -> _emptyError.value = msg } }
            )
    }

    override fun onBackPressed() = router.exit()
}
