package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.converters.LocalDateTimeConverter
import com.akhbulatov.discusim.data.global.network.interceptors.AuthInterceptor
import com.akhbulatov.discusim.data.session.OAuthParams
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
object NetworkModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideOAuthParams() = OAuthParams(
        "${BuildConfig.BASE_URL}api/oauth/2.0/authorize/",
        BuildConfig.CLIENT_ID,
        BuildConfig.CLIENT_SECRET,
        BuildConfig.OAUTH_SCOPE,
        BuildConfig.REDIRECT_URI
    )

    @JvmStatic
    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        if (BuildConfig.DEBUG) {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .addNetworkInterceptor(StethoInterceptor())
        .build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(LocalDateTimeConverter())
        .build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_API_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideDisqusApi(retrofit: Retrofit): DisqusApi = retrofit.create()
}