package com.akhbulatov.discusim.presentation.ui.about

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akhbulatov.discusim.domain.app.AppInteractor
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.presentation.global.BaseViewModel
import com.akhbulatov.discusim.presentation.global.FlowRouter
import com.akhbulatov.discusim.presentation.global.Screens
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import javax.inject.Inject

class AboutViewModel @Inject constructor(
    private val router: FlowRouter,
    private val appInteractor: AppInteractor,
    private val schedulers: SchedulersProvider
) : BaseViewModel() {

    private val _appInfo = MutableLiveData<AppInfo>()
    val appInfo: LiveData<AppInfo> get() = _appInfo

    init {
        loadAppInfo()
    }

    private fun loadAppInfo() {
        disposables += appInteractor.getAppInfo()
            .observeOn(schedulers.ui())
            .subscribeBy(
                onSuccess = { _appInfo.value = it },
                onError = { Timber.e("Failed to load app info: $it") }
            )
    }

    fun onLibrariesClicked() = router.navigateTo(Screens.AppLibraries)

    override fun onBackPressed() = router.exit()
}
