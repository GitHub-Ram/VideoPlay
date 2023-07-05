package com.dazngroup.videoplay

import android.app.Application
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateOf
import androidx.core.os.bundleOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dazngroup.videoplay.interfaces.AnalyticsProvider
import com.dazngroup.videoplay.interfaces.VideoProvider
import com.dazngroup.videoplay.models.VideoDetail
import com.dazngroup.videoplay.utils.Pages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    var bitmaps = mutableStateOf(mutableMapOf<String,Bitmap>())
    var currentPage = mutableStateOf(Pages.LISTING)
    private var _videos = MutableLiveData<Array<VideoDetail>>()
    val videos: LiveData<Array<VideoDetail>> = _videos
    val analyticsState = mutableStateOf(emptyArray<Pair<String,Int>>())
    val isPlayerPlaying = mutableStateOf(true)
    var currentVideo : VideoDetail? = null
        set(value) {
            field = value
            val videos = _videos.value
            videos?.let {
                currentIndex = it.indexOfFirst { item -> item == value }
            }
        }

    var currentIndex : Int = 0

    @Inject
    lateinit var videoProvider: VideoProvider

    @Inject
    lateinit var analyticsProvider: AnalyticsProvider

    fun fetchVideos(){
        CoroutineScope(Dispatchers.IO).launch {
            val videos = videoProvider.getVideos()
            _videos.postValue(videos)
        }
    }

    fun switchPage(videoDetail: VideoDetail?) {
        if (currentPage.value == Pages.LISTING){
            currentPage.value = Pages.PLAYBACK
            currentVideo = videoDetail
        }else{
            currentPage.value = Pages.LISTING
        }
    }

    fun onPlayerControlClicked(newState: String) {
        var index = analyticsState.value.indexOfFirst{ it->it.first == newState }
        val backPair = if (index>=0) analyticsState.value[index] else null
        val newPair : Pair<String,Int> = backPair?.let {
            Pair(it.first,it.second + 1)
        } ?: run {
            Pair(newState, 1)
        }
        if (index>=0) {
            analyticsState.value[index] = newPair
            var newArray = emptyArray<Pair<String,Int>>()
            newArray += analyticsState.value
            analyticsState.value = emptyArray()
            analyticsState.value += newArray
        }
        else
            analyticsState.value += newPair

        analyticsProvider.sendEvent("Video_Event", bundleOf(Pair(newState.replace(" ","_"),"Count")))
    }
}

