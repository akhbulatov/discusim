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
        Single.fromCallable { fileManager.readRawFile("app_libraries.json") }
            .subscribeOn(schedulers.io())
            .map { libraryResponseParser.parse(it) }
            .map { libraryResponseMapper.map(it) }
            .observeOn(schedulers.computation())
}
