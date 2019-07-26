package com.akhbulatov.discusim.domain.library

import com.akhbulatov.discusim.domain.global.models.AppLibrary
import com.akhbulatov.discusim.domain.global.repositories.LibraryRepository
import io.reactivex.Single
import javax.inject.Inject

class LibraryInteractor @Inject constructor(
    private val libraryRepository: LibraryRepository
) {

    fun getAppLibraries(): Single<List<AppLibrary>> =
        libraryRepository.getAppLibraries()
}
