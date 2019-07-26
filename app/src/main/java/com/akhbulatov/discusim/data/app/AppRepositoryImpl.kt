package com.akhbulatov.discusim.data.app

import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.domain.global.repositories.AppRepository
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appInfo: AppInfo,
    private val schedulers: SchedulersProvider
) : AppRepository {

    override fun getAppInfo(): Single<AppInfo> =
        Single.just(appInfo)
            .subscribeOn(schedulers.io())
}
