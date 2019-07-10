package com.akhbulatov.discusim.data.global.network

import com.akhbulatov.discusim.BuildConfig
import com.akhbulatov.discusim.data.comment.CommentsResponse
import com.akhbulatov.discusim.data.discussion.DiscussionResponse
import com.akhbulatov.discusim.data.discussion.DiscussionsResponse
import com.akhbulatov.discusim.data.follower.FollowersResponse
import com.akhbulatov.discusim.data.following.FollowingResponse
import com.akhbulatov.discusim.data.forum.ForumResponse
import com.akhbulatov.discusim.data.forum.ForumsResponse
import com.akhbulatov.discusim.data.global.network.models.SessionNetModel
import com.akhbulatov.discusim.data.global.network.models.vote.VoteResponse
import com.akhbulatov.discusim.data.topic.TopicsResponse
import com.akhbulatov.discusim.data.user.UserResponse
import io.reactivex.Completable
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

    @FormUrlEncoded
    @POST("forums/follow.json")
    fun followForum(@Field("target") forumId: String): Completable

    @FormUrlEncoded
    @POST("forums/unfollow.json")
    fun unfollowForum(@Field("target") forumId: String): Completable
    // --- Forum --- //

    // --- Discussions --- //
    @GET("threads/details.json")
    fun getDiscussionDetails(
        @Query("thread") discussionId: Long,
        @Query("related") related: List<String>,
        @Query("attach") attach: List<String>
    ): Single<DiscussionResponse>

    @GET("threads/list.json")
    fun getDiscussions(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>,
        @Query("attach") attach: List<String>
    ): Single<DiscussionsResponse>

    @GET("threads/listHot.json")
    fun getHotDiscussions(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<DiscussionsResponse>

    @GET("threads/listPopular.json")
    fun getPopularDiscussions(
        @Query("forum") forumId: String,
        @Query("related") related: List<String>
    ): Single<DiscussionsResponse>

    @FormUrlEncoded
    @POST("threads/vote.json")
    fun voteDiscussion(
        @Field("thread") discussionId: Long,
        @Field("vote") vote: Int
    ): Single<VoteResponse>
    // --- Discussions --- //
}