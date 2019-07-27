package com.akhbulatov.discusim.di.modules

import android.content.res.AssetManager
import com.akhbulatov.discusim.data.global.filesystem.FileManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object FileSystemModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideFileManager(assetManager: AssetManager) = FileManager(assetManager)
}
