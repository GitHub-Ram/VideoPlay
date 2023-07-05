package com.dazngroup.videoplay

import android.app.Application
import android.content.Context
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.dazngroup.videoplay.interfaces.AnalyticsProvider
import com.dazngroup.videoplay.interfaces.VideoProvider
import com.dazngroup.videoplay.models.VideoDetail
import com.dazngroup.videoplay.providers.FirebaseAnalyticsProvider
import com.dazngroup.videoplay.providers.LocalVideoSource
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Dispatcher
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    lateinit var videoProvider: VideoProvider

    lateinit var analyticsProvider: AnalyticsProvider


    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        videoProvider = LocalVideoSource(context)
        analyticsProvider = FirebaseAnalyticsProvider(context)
        viewModel = MainViewModel(context as Application)
        viewModel.videoProvider = videoProvider
        viewModel.analyticsProvider = analyticsProvider
    }

    @Test
    fun testLocalVideoList() {
        GlobalScope.launch {
            viewModel.fetchVideos()
            val size = viewModel.videos.value?.size ?: 0
            assertEquals(6, size)
        }
    }

    @Test
    fun testAnalyticsPause() {
        viewModel.onPlayerControlClicked("Pause")
        viewModel.onPlayerControlClicked("Pause")
        viewModel.onPlayerControlClicked("Pause")
        viewModel.onPlayerControlClicked("Pause")
        viewModel.onPlayerControlClicked("Pause")
        val pauseEvent = viewModel.analyticsState.value.filter { x->x.first == "Pause" }
        assertEquals(5, pauseEvent[0].second)
    }

    @Test
    fun testAnalyticsForward() {
        viewModel.onPlayerControlClicked("Forward")
        viewModel.onPlayerControlClicked("Forward")
        viewModel.onPlayerControlClicked("Forward")
        viewModel.onPlayerControlClicked("Pause")
        viewModel.onPlayerControlClicked("Forward")
        val pauseEvent = viewModel.analyticsState.value.filter { x->x.first == "Forward" }
        assertEquals(4, pauseEvent[0].second)
    }
}