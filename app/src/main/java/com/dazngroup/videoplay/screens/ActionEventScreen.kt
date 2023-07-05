package com.dazngroup.videoplay.screens


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dazngroup.videoplay.MainViewModel

@Composable
fun actionEventScreen(viewModel: MainViewModel) {
    var analytics = remember { viewModel.analyticsState }
    LazyColumn(modifier = Modifier.fillMaxWidth(),content = {
        items(analytics.value){
            Text(text = "${it.first} : ${it.second}", modifier = Modifier.padding(6.dp))
        }
    })
}


