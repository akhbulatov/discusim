package com.akhbulatov.discusim.di.modules

import com.akhbulatov.discusim.data.global.filesystem.FileManager
import com.akhbulatov.discusim.data.global.filesystem.RawFileManager
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class FileSystemModule {
    @Binds
    @Singleton
    abstract fun provideRawFileManager(fileManager: RawFileManager): FileManager
}
