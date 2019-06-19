package com.akhbulatov.discusim.data.global.network

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.forums.ForumResponse
import com.akhbulatov.discusim.data.forums.ForumsResponse
import com.akhbulatov.discusim.data.forums.ModeratorsResponse
import com.akhbulatov.discusim.data.global.network.models.AuthNetModel
import com.akhbulatov.discusim.data.threads.ThreadsResponse
import com.akhbulatov.discusim.data.users.UserDetailsResponse
import com.akhbulatov.discusim.data.users.UserPostsResponse
import com.akhbulatov.discusim.data.users.UsersResponse
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
    ): Single<AuthNetModel>

    @GET("users/details.json")
    fun getUser(@Query("user") userId: Long): Single<UserDetailsResponse>

    @GET("users/listActivity.json")
    fun getUserActivities(@Query("user") userId: Long): Single<ResponseBody>

    @GET("users/listPosts.json")
    fun getUserPosts(@Query("user") userId: Long): Single<UserPostsResponse>

    @GET("users/listFollowers.json")
    fun getFollowers(@Query("user") userId: Long): Single<UsersResponse>

    @GET("users/listFollowing.json")
    fun getFollowingUsers(@Query("user") userId: Long): Single<UsersResponse>

    @GET("users/listFollowingForums.json")
    fun getFollowingForums(@Query("user") userId: Long): Single<ForumsResponse>

    @GET("forums/details.json")
    fun getForumDetails(
        @Query("forum") forumId: String,
        @Query("attach") attachList: List<String>
    ): Single<ForumResponse>

    @GET("forums/listThreads.json")
    fun getThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>

    @GET("forums/listMostActiveUsers.json")
    fun getForumMostActiveUsers(@Query("forum") forumId: String): Single<UsersResponse>

    @GET("forums/listModerators.json")
    fun getForumModerators(@Query("forum") forumId: String): Single<ModeratorsResponse>

    @GET("threads/listHot.json")
    fun getHotThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>

    @GET("threads/listPopular.json")
    fun getPopularThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<ThreadsResponse>
}