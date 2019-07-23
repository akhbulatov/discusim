package com.akhbulatov.discusim.data.global.network.interceptors

import com.akhbulatov.discusim.data.global.local.prefs.PreferencesStorage
import com.akhbulatov.discusim.data.session.OAuthParams
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val prefsStorage: PreferencesStorage,
    private val oAuthParams: OAuthParams
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        var url = request.url

        prefsStorage.accessToken?.let {
            url = url.newBuilder()
                .addQueryParameter("api_key", oAuthParams.clientId)
                .addQueryParameter("api_secret", oAuthParams.clientSecret)
                .addQueryParameter("access_token", it)
                .build()

            request = request.newBuilder()
                .url(url)
                .build()
        }

        return chain.proceed(request)
    }
}
