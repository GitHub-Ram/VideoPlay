package com.dazngroup.videoplay.providers

import android.content.Context
import com.dazngroup.videoplay.models.VideoDetail
import com.dazngroup.videoplay.interfaces.VideoProvider
import com.google.gson.Gson
import java.io.InputStream
import javax.inject.Inject

class LocalVideoSource @Inject constructor(private val application: Context): VideoProvider {
    private val videoList : Array<VideoDetail> by lazy { Gson().fromJson(readJSONFromAsset(), Array<VideoDetail>::class.java) }
    private fun readJSONFromAsset(): String? {
        var json: String? = null
        try {
            val  inputStream: InputStream = application.assets.open("videos.json")
            json = inputStream.bufferedReader().use{it.readText()}
        } catch (ex: Exception) {
            ex.printStackTrace()
            return null
        }
        return json
    }
    override suspend fun getVideos(): Array<VideoDetail> {
        return videoList
    }
}