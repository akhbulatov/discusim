package com.akhbulatov.discusim.domain.app

import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.domain.global.repositories.AppRepository
import io.reactivex.Single
import javax.inject.Inject

class AppInteractor @Inject constructor(
    private val appRepository: AppRepository
) {

    fun getAppInfo(): Single<AppInfo> =
        appRepository.getAppInfo()
}
