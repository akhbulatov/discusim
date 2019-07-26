package com.akhbulatov.discusim.data.app

import com.akhbulatov.discusim.domain.global.models.AppInfo
import com.akhbulatov.discusim.domain.global.repositories.AppRepository
import io.reactivex.Single
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val appInfo: AppInfo
) : AppRepository {

    override fun getAppInfo(): Single<AppInfo> = Single.just(appInfo)
}
