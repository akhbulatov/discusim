package com.akhbulatov.discusim.data.global.filesystem

import android.content.res.AssetManager
import javax.inject.Inject

/**
 * Управляет сырыми (raw) файлами, например из asset.
 */
class RawFileManager @Inject constructor(
    private val assetManager: AssetManager
) : FileManager {

    override fun readFile(fileName: String): String {
        val reader = assetManager.open(fileName).bufferedReader()
        return reader.use { it.readText() }
    }
}
