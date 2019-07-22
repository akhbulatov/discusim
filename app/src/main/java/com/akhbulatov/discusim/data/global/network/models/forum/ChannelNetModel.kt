package com.akhbulatov.discusim.data.global.network.models.forum

import com.akhbulatov.discusim.data.global.network.utils.HexColor
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ChannelNetModel(
    @Json(name = "id") val id: String,
    @Json(name = "avatar") val avatar: String?,
    @Json(name = "banner") val banner: String?,
    @Json(name = "bannerColorHex") @HexColor val bannerColorHex: Int,
    @Json(name = "options") val options: OptionsNetModel
) {

    @JsonClass(generateAdapter = true)
    data class OptionsNetModel(@Json(name = "alertBackground") val alertBackground: String?)
}