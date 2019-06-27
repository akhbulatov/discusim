package com.akhbulatov.discusim.data.global.network

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.forum.ForumResponse
import com.akhbulatov.discusim.data.forum.ForumsResponse
import com.akhbulatov.discusim.data.forum.ModeratorsResponse
import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import com.akhbulatov.discusim.data.thread.ThreadsResponse
import com.akhbulatov.discusim.data.thread.TrendThreadsResponse
import com.akhbulatov.discusim.data.user.UserDetailsResponse
import com.akhbulatov.discusim.data.user.UserPostsResponse
import com.akhbulatov.discusim.data.user.UsersResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface DisqusApi {
    @FormUrlEncoded
    @POST("${BuildConfig.BASE_URL}api/oauth/2.0/access_token/")
    fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String
    ): Single<SessionNetModel>

    @GET("users/listActivity.json")
    fun getUserActivity(
        @Query("user") userId: Long,
        @Query("cursor") cursor: String?
    ): Single<ResponseBody>

    @GET("users/listFollowingForums.json")
    fun getFollowingForums(
        @Query("user") userId: Long,
        @Query("cursor") cursor: String?
    ): Single<ForumsResponse>

    @GET("forums/details.json")
    fun getForumDetails(@Query("forum") forumId: String): Single<ForumResponse>

    @GET("threads/listHot.json")
    fun getHotThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>

    @GET("trends/listThreads.json")
    fun getTrendThreads(
        @Query("forum") forumId: String?,
        @Query("related") related: List<String>
    ): Single<TrendThreadsResponse>

    // --- //
    @GET("users/details.json")
    fun getUser(@Query("user") userId: Long): Single<UserDetailsResponse>

    @GET("users/listPosts.json")
    fun getUserPosts(@Query("user") userId: Long): Single<UserPostsResponse>

    @GET("users/listFollowers.json")
    fun getFollowers(@Query("user") userId: Long): Single<UsersResponse>

    @GET("users/listFollowing.json")
    fun getFollowingUsers(@Query("user") userId: Long): Single<UsersResponse>

    @GET("forums/listThreads.json")
    fun getThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>

    @GET("forums/listMostActiveUsers.json")
    fun getForumMostActiveUsers(@Query("forum") forumId: String): Single<UsersResponse>

    @GET("forums/listModerators.json")
    fun getForumModerators(@Query("forum") forumId: String): Single<ModeratorsResponse>

    @GET("threads/listPopular.json")
    fun getPopularThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>
}