package com.playrix.gar.application.modules

import android.content.Context
import com.playrix.gar.data.photo.ImageCreationTemporaryParams
import com.playrix.gar.data.api.link.LinkParts
import com.playrix.gar.data.api.link.SecondaryLinkParts
import com.playrix.gar.some_services.services.facebook.FacebookImpl
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
    fun provideLinkData() = LinkParts()

    @Provides
    @Singleton
    fun provideSideData() = SecondaryLinkParts()

    @Provides
    @Singleton
    fun providePhotoManagerData() = ImageCreationTemporaryParams()

    @Provides
    @Singleton
    fun provideFacebookImpl(@ApplicationContext appContext: Context) = FacebookImpl(appContext)

    @Provides
    @Singleton
    fun provideDownloadingManager(@ApplicationContext appContext: Context) = DownloadingManager(appContext)
}