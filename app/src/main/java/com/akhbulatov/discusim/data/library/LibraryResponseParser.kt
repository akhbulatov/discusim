package com.akhbulatov.discusim.data.library

import com.akhbulatov.discusim.data.global.network.models.AppLibraryNetModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import javax.inject.Inject

class LibraryResponseParser @Inject constructor(
    private val moshi: Moshi
) {

    fun parse(appLibrariesJson: String): List<AppLibraryNetModel> {
        val type = Types.newParameterizedType(List::class.java, AppLibraryNetModel::class.java)
        val adapter = moshi.adapter<List<AppLibraryNetModel>>(type)
        return adapter.fromJson(appLibrariesJson)!!
    }
}
