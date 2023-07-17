package com.playrix.gar.application.modules

import android.content.Context
import com.playrix.gar.some_services.services.downloader.DownloadingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltBaseModule {
    @Provides
    @Singleton
    fun provideDownloadingManager(@ApplicationContext appContext: Context) = DownloadingManager(appContext)
}