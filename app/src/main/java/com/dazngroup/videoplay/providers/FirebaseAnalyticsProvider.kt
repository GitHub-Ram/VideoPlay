package com.dazngroup.videoplay.providers

import android.content.Context
import android.os.Bundle
import com.dazngroup.videoplay.interfaces.AnalyticsProvider
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class FirebaseAnalyticsProvider @Inject constructor(private val application: Context) : AnalyticsProvider {
    private val firebaseAnalytics = FirebaseAnalytics.getInstance(application)

    override fun sendEvent(eventName: String, eventParam: Bundle) {
        firebaseAnalytics.logEvent(eventName, eventParam)
    }
}