package com.dazngroup.videoplay.modules

import android.app.Application
import com.dazngroup.videoplay.interfaces.VideoProvider
import com.dazngroup.videoplay.providers.LocalVideoSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class VideoProviderModule {
    @Provides
    fun getLocalVideos(application: Application): VideoProvider {
        return LocalVideoSource(application)
    }
}