package com.akhbulatov.discusim.data.global.network

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.domain.global.models.Auth
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface DisqusApi {
    @FormUrlEncoded
    @POST("${BuildConfig.BASE_URL}api/oauth/2.0/access_token/")
    fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String
    ): Single<Auth>
}