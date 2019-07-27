package com.akhbulatov.discusim.data.global.network.interceptors

import com.akhbulatov.discusim.data.global.network.utils.NetworkChecker
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineCacheInterceptor @Inject constructor(
    private val networkChecker: NetworkChecker
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!networkChecker.hasConnection()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }
        return chain.proceed(request)
    }
}
