package com.dazngroup.videoplay.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.dazngroup.videoplay.MainViewModel
import com.dazngroup.videoplay.models.VideoDetail
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior

@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnsafeOptInUsageError")
@Composable
fun videoPlayBackScreen(videos: Array<VideoDetail>, currentIndex: Int, viewModel: MainViewModel) {

    BackHandler() {
        viewModel.switchPage(null)
    }

    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        flingBehavior = rememberSnapFlingBehavior(listState),
    ) {
        items(videos) { item ->
            Box(modifier = Modifier
                .background(color = Color(0xFFEEEEEE))
                .fillParentMaxHeight(1f)
                .fillParentMaxWidth(1f)){
                Column() {
                    ExoPlayerView(item,viewModel)
                    actionEventScreen(viewModel = viewModel)
                }

            }
        }
    }
    if(currentIndex!=0) {
        LaunchedEffect(currentIndex){
            listState.scrollToItem(index = currentIndex)
        }
    }
}