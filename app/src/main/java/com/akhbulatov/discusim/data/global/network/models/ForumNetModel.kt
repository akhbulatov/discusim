package com.akhbulatov.discusim.data.global.network.models

import com.squareup.moshi.Json

data class ForumNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "name") val name: String,
    @Json(name = "description") val description: String?,
    @Json(name = "favicon") val favicon: Favicon,
    @Json(name = "url") val url: String,
    @Json(name = "numThreads") val numThreads: Int?,
    @Json(name = "numFollowers") val numFollowers: Int?,
    @Json(name = "numModerators") val numModerators: Int?,
    @Json(name = "channel") val channel: Channel?
) {

    data class Favicon(@Json(name = "permalink") val link: String)

    data class Channel(
        @Json(name = "bannerColorHex") val bannerColor: String,
        @Json(name = "options") val options: Options
    ) {

        data class Options(
            @Json(name = "description") val description: String,
            @Json(name = "alertBackground") val backgroundUrl: String,
            @Json(name = "titleLogo") val logo: Logo,
            @Json(name = "aboutUrlPath") val guidelinesUrl: String
        ) {

            data class Logo(@Json(name = "small") val url: String)
        }
    }
}