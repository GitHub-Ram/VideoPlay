package com.dazngroup.videoplay.interfaces

import com.dazngroup.videoplay.models.VideoDetail

interface VideoProvider {
    suspend fun getVideos():Array<VideoDetail>
}