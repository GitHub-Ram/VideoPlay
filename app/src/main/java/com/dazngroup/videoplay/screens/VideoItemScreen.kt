package com.dazngroup.videoplay.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.dazngroup.videoplay.MainViewModel
import com.dazngroup.videoplay.R
import com.dazngroup.videoplay.models.VideoDetail

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun videoItem(videoDetail: VideoDetail, viewModel: MainViewModel){
    Card(elevation = 4.dp, modifier = Modifier
        .padding(8.dp)
        .fillMaxWidth(), onClick = {
        viewModel.switchPage(videoDetail)
    }) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            if(viewModel.bitmaps.value.containsKey(videoDetail.uri)){
                Image(
                    painter = rememberImagePainter(data = viewModel.bitmaps.value.get(videoDetail.uri)),
                    contentDescription = "Video Thumbnail",
                    modifier = Modifier
                        .size(80.dp, 60.dp)
                        .clip(RoundedCornerShape(4.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }else{
                ImageWithBorderAndCornerRadius()
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Box(modifier = Modifier
                .background(color = Color(0xFFEEEEEE))
                .fillMaxHeight(.8f)
                .width(1.dp))
            Text(text = videoDetail.name,
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(0.dp,0.dp,0.dp,8.dp)
            )
        }
    }
}

@Composable
fun ImageWithBorderAndCornerRadius() {
    val cornerRadius = 4.dp
    val borderColor = Color.Gray
    val borderWidth = 1.dp

    Box(
        modifier = Modifier
            .border(borderWidth, borderColor, shape = RoundedCornerShape(cornerRadius))
            .clip(RoundedCornerShape(cornerRadius))
            .size(80.dp, 60.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.youtube),
            contentDescription = "Placeholder",
            modifier = Modifier.size(50.dp)

        )
    }
}