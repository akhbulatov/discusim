package com.akhbulatov.discusim.di.modules

import android.content.Context
import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.global.network.DisqusApi
import com.akhbulatov.discusim.data.global.network.converters.ColorConverter
import com.akhbulatov.discusim.data.global.network.converters.LocalDateTimeConverter
import com.akhbulatov.discusim.data.global.network.interceptors.AuthInterceptor
import com.akhbulatov.discusim.data.global.network.interceptors.CacheInterceptor
import com.akhbulatov.discusim.data.global.network.interceptors.OfflineCacheInterceptor
import com.akhbulatov.discusim.data.session.OAuthParams
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File
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
    fun provideStethoInterceptor() = StethoInterceptor()

    @JvmStatic
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache {
        val cacheSize: Long = 10 * 1024 * 1024 // 10 MB
        val httpCacheDirectory = File(context.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, cacheSize)
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        stethoInterceptor: StethoInterceptor,
        authInterceptor: AuthInterceptor,
        offlineCacheInterceptor: OfflineCacheInterceptor,
        cacheInterceptor: CacheInterceptor,
        cache: Cache
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .addInterceptor(authInterceptor)
        .addInterceptor(offlineCacheInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .addNetworkInterceptor(stethoInterceptor)
        .cache(cache)
        .build()

    @JvmStatic
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(LocalDateTimeConverter())
        .add(ColorConverter())
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
