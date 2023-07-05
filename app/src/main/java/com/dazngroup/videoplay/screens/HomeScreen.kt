package com.dazngroup.videoplay.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.dazngroup.videoplay.MainViewModel
import com.dazngroup.videoplay.models.VideoDetail


@Composable
fun videoListScreen(videoDetails:Array<VideoDetail>, viewModel: MainViewModel){
    Column() {
        Text(
            text = "Videos",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp, 16.dp)
                .fillMaxWidth(),
            style = MaterialTheme.typography.h5
        )
        videoList(videoDetails,viewModel)
    }
}

@Composable
fun videoList(videoDetails:Array<VideoDetail>, viewModel: MainViewModel){
    LazyColumn(content = {
        items(videoDetails){
            videoItem(it,viewModel)
        }
    })
}