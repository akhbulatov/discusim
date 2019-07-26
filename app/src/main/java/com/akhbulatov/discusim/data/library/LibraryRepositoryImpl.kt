package com.akhbulatov.discusim.data.library

import com.akhbulatov.discusim.data.global.filesystem.FileManager
import com.akhbulatov.discusim.domain.global.SchedulersProvider
import com.akhbulatov.discusim.domain.global.models.AppLibrary
import com.akhbulatov.discusim.domain.global.repositories.LibraryRepository
import io.reactivex.Single
import javax.inject.Inject

class LibraryRepositoryImpl @Inject constructor(
    private val fileManager: FileManager,
    private val libraryResponseParser: LibraryResponseParser,
    private val libraryResponseMapper: LibraryResponseMapper,
    private val schedulers: SchedulersProvider
) : LibraryRepository {

    override fun getAppLibraries(): Single<List<AppLibrary>> =
        Single.fromCallable { fileManager.readFile("app_libraries.json") }
            .map { libraryResponseParser.parse(it) }
            .map { libraryResponseMapper.map(it) }
            .subscribeOn(schedulers.io())
}
