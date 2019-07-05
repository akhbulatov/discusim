package com.akhbulatov.discusim.data.global.network.converters

import androidx.core.graphics.toColorInt
import com.akhbulatov.discusim.data.global.network.utils.HexColor
import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson

class ColorConverter {
    @ToJson
    fun toJson(@HexColor color: Int): String = color.toHexColor()

    @FromJson
    @HexColor
    fun fromJson(hex: String): Int = hex.toColorInt()
}

private fun Int.toHexColor(): String = String.format("#%06X", 0xFFFFFF and this)