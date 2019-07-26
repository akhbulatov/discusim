package com.akhbulatov.discusim.data.global.filesystem

interface FileManager {
    fun readFile(fileName: String): String
}
