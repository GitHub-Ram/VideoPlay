package com.dazngroup.videoplay.modules

import android.app.Application
import com.dazngroup.videoplay.interfaces.AnalyticsProvider
import com.dazngroup.videoplay.interfaces.VideoProvider
import com.dazngroup.videoplay.providers.FirebaseAnalyticsProvider
import com.dazngroup.videoplay.providers.LocalVideoSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
class AnalyticsProviderModule {
    @Provides
    fun getFirebaseAnalytics(application: Application): AnalyticsProvider {
        return FirebaseAnalyticsProvider(application)
    }
}