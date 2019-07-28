package com.akhbulatov.discusim.data.library

import com.akhbulatov.discusim.data.global.network.models.AppLibraryNetModel
import com.akhbulatov.discusim.domain.global.models.AppLibrary
import javax.inject.Inject

class LibraryResponseMapper @Inject constructor() {

    fun map(models: List<AppLibraryNetModel>): List<AppLibrary> =
        models.map { map(it) }

    fun map(model: AppLibraryNetModel): AppLibrary =
        model.let {
            AppLibrary(
                it.name,
                it.author,
                it.license,
                it.webUrl
            )
        }
}
