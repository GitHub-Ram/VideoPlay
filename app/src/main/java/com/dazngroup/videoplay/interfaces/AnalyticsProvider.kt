package com.dazngroup.videoplay.interfaces

import android.os.Bundle

interface AnalyticsProvider {
    fun sendEvent(eventName:String,eventParam : Bundle)
}