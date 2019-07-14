package com.akhbulatov.discusim.data.global.network.models

import com.akhbulatov.discusim.data.global.network.utils.HexColor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForumNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "favicon") val favicon: Favicon,
    @Json(name = "url") val url: String,
    @Json(name = "isFollowing") val isFollowing: Boolean,
    @Json(name = "numThreads") val numThreads: Int,
    @Json(name = "numFollowers") val numFollowers: Int,
    @Json(name = "channel") val channel: ChannelNetModel?
) {

    @JsonClass(generateAdapter = true)
    data class Favicon(@Json(name = "permalink") val permalink: String)

    @JsonClass(generateAdapter = true)
    data class ChannelNetModel(
        @Json(name = "id") val id: String,
        @Json(name = "avatar") val avatar: String,
        @Json(name = "banner") val banner: String?,
        @Json(name = "bannerColorHex") @HexColor val bannerColorHex: Int,
        @Json(name = "options") val options: OptionsNetModel
    ) {

        @JsonClass(generateAdapter = true)
        data class OptionsNetModel(@Json(name = "alertBackground") val alertBackground: String?)
    }
}