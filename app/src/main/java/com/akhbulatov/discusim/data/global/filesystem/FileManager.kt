package com.akhbulatov.discusim.data.global.filesystem

import android.content.res.AssetManager

class FileManager(
    private val assetManager: AssetManager
) {

    fun readRawFile(fileName: String): String {
        val reader = assetManager.open(fileName).bufferedReader()
        return reader.use { it.readText() }
    }
}
