package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.AppInfo
import io.reactivex.Single

interface AppRepository {
    fun getAppInfo(): Single<AppInfo>
}
