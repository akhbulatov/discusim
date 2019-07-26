package com.akhbulatov.discusim.domain.global.repositories

import com.akhbulatov.discusim.domain.global.models.AppLibrary
import io.reactivex.Single

interface LibraryRepository {
    fun getAppLibraries(): Single<List<AppLibrary>>
}
