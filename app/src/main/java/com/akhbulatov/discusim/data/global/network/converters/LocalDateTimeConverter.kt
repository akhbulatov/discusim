package com.akhbulatov.discusim.data.global.network.converters

import com.squareup.moshi.FromJson
import org.threeten.bp.LocalDateTime

class LocalDateTimeConverter {
    @FromJson
    fun fromJson(json: String): LocalDateTime = LocalDateTime.parse(json)
}