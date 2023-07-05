package com.dazngroup.videoplay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.dazngroup.videoplay.screens.videoListScreen
import com.dazngroup.videoplay.screens.videoPlayBackScreen
import com.dazngroup.videoplay.ui.theme.VideoPlayTheme
import com.dazngroup.videoplay.utils.Pages
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.fetchVideos()
        setContent {
            VideoPlayTheme {
                homeScreen()
            }
        }
    }

    @Composable
    fun homeScreen() {
        val data = viewModel.videos.observeAsState()
        data.value?.let {
            if(viewModel.currentPage.value == Pages.LISTING){
                videoListScreen(it,viewModel)
            }else{
                videoPlayBackScreen(it,viewModel.currentIndex,viewModel)
            }
        }?: run {
            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize(1f)) {
                Text(text = "Loading..", style = MaterialTheme.typography.h6)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.isPlayerPlaying.value = true
    }

    override fun onPause() {
        super.onPause()
        viewModel.isPlayerPlaying.value = false
    }
}