package com.akhbulatov.discusim.data.global.network

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.comment.CommentsResponse
import com.akhbulatov.discusim.data.follower.FollowersResponse
import com.akhbulatov.discusim.data.following.FollowingResponse
import com.akhbulatov.discusim.data.forum.ForumResponse
import com.akhbulatov.discusim.data.forum.ForumsResponse
import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import com.akhbulatov.discusim.data.thread.ThreadsResponse
import com.akhbulatov.discusim.data.topic.TopicsResponse
import com.akhbulatov.discusim.data.user.UserResponse
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API сервиса Disqus спроектирован так, что если параметр **user** в запросах равен `null`,
 * запрашивает данные для авторизованного юзера по access token. Поэтому данный параметр `nullable` в тех методах,
 * в которых он используется.
 *
 * Для пагинации используется курсор.
 */
interface DisqusApi {
    // --- Auth --- //
    @FormUrlEncoded
    @POST("${BuildConfig.BASE_URL}api/oauth/2.0/access_token/")
    fun login(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("redirect_uri") redirectUri: String,
        @Field("code") code: String
    ): Single<SessionNetModel>
    // --- Auth --- //

    // --- Users --- //
    @GET("users/details.json")
    fun getUserDetails(
        @Query("user") userId: Long
    ): Single<UserResponse>

    @GET("users/listActivity.json")
    fun getUserActivity(
        @Query("user") userId: Long?,
        @Query("cursor") cursor: String?
    ): Single<ResponseBody>

    @GET("users/listFollowingForums.json")
    fun getFollowingForums(
        @Query("user") userId: Long?,
        @Query("cursor") cursor: String?,
        @Query("attach") attach: List<String>
    ): Single<ForumsResponse>

    @GET("users/listPosts.json")
    fun getUserComments(
        @Query("user") userId: Long,
        @Query("cursor") cursor: String?,
        @Query("related") related: List<String>
    ): Single<CommentsResponse>

    @GET("users/listFollowers.json")
    fun getUserFollowers(
        @Query("user") userId: Long,
        @Query("cursor") cursor: String?
    ): Single<FollowersResponse>

    @GET("users/listFollowing.json")
    fun getUserFollowing(
        @Query("user") userId: Long,
        @Query("cursor") cursor: String?
    ): Single<FollowingResponse>
    // --- Users --- //

    // --- Forum --- //
    @GET("forums/details.json")
    fun getForumDetails(
        @Query("forum") forumId: String,
        @Query("attach") attach: List<String>
    ): Single<ForumResponse>

    @GET("channels/listTrendingTopics")
    fun getTrendingTopics(
        @Query("channel") forumId: String
    ): Single<TopicsResponse>
    // --- Forum --- //

    // --- Threads --- //
    @GET("threads/list.json")
    fun getThreads(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>,
        @Query("attach") attach: List<String>
    ): Single<ThreadsResponse>

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
    // --- Threads --- //
}