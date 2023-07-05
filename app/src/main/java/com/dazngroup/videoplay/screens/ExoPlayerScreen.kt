package com.dazngroup.videoplay.screens

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.media.session.PlaybackState.STATE_FAST_FORWARDING
import android.media.session.PlaybackState.STATE_PAUSED
import android.media.session.PlaybackState.STATE_PLAYING
import android.media.session.PlaybackState.STATE_REWINDING
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.view.PixelCopy
import android.view.SurfaceView
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.get
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.Timeline
import androidx.media3.datasource.DefaultDataSourceFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.dash.DashMediaSource
import androidx.media3.exoplayer.dash.DefaultDashChunkSource
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import androidx.media3.ui.R
import com.dazngroup.videoplay.MainViewModel
import com.dazngroup.videoplay.models.VideoDetail

@SuppressLint("UnsafeOptInUsageError")
@Composable
fun ExoPlayerView(video: VideoDetail, viewModel: MainViewModel) {
    val context = LocalContext.current
    val exoPlayer = rememberExoPlayer(context,video.uri)
    exoPlayer.pause()

    val isPlayerPlaying by viewModel.isPlayerPlaying
    if (!isPlayerPlaying) {
        exoPlayer.pause()
    }

    DisposableEffect(
        AndroidView(
            modifier = Modifier.fillMaxWidth().aspectRatio(1.6f),
            factory = { context ->
                PlayerView(context).apply {
                    player = exoPlayer
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_FILL
                    setShowNextButton(false)
                    setShowPreviousButton(false)
                    setShowBuffering(PlayerView.SHOW_BUFFERING_ALWAYS)
                    hideController()
                }
            },
            update = { view ->
                val sbc = view.getChildAt(3)
                sbc?.let {
                    val back = ((it as ViewGroup).getChildAt(4) as ViewGroup).getChildAt(1)
                    back?.let {
                        it.setOnTouchListener( View.OnTouchListener { view, event ->
                            if (event.action == MotionEvent.ACTION_UP) {
                                viewModel.onPlayerControlClicked("Backward Count")
                            }
                            false
                        })
                    }

                    val playPause = ((it as ViewGroup).getChildAt(4) as ViewGroup).getChildAt(2)
                    playPause?.let {
                        it.setOnTouchListener( View.OnTouchListener { view, event ->
                            if (event.action == MotionEvent.ACTION_UP){
                                val text = if (exoPlayer.playWhenReady) "Paused Count" else "Play Count"
                                viewModel.onPlayerControlClicked(text)
                            }
                            false
                        })
                    }

                    val forward = ((it as ViewGroup).getChildAt(4) as ViewGroup).getChildAt(3)
                    forward?.let {
                        it.setOnTouchListener( View.OnTouchListener { view, event ->
                            if (event.action == MotionEvent.ACTION_UP){
                                viewModel.onPlayerControlClicked("Forward Count")
                            }
                            false
                        })
                    }
                }

                view.player?.addListener(object : Player.Listener {
                    override fun onIsPlayingChanged(isPlaying: Boolean) {
                        if (isPlaying) {
                            val surfaceView = view.videoSurfaceView as? SurfaceView
                            surfaceView?.let {
                                if (it.height>0){
                                    val bitmap = Bitmap.createBitmap(it.width, it.height, Bitmap.Config.ARGB_8888)
                                    PixelCopy.request(it, bitmap, { result ->
                                        if (result == PixelCopy.SUCCESS) {
                                            viewModel.bitmaps.value.set(video.uri,bitmap)
                                        } }, Handler(Looper.getMainLooper()))
                                }
                            }
                        }
                    }
                })
                view.player?.playWhenReady = true
            }
        )
    ){
        onDispose {
            exoPlayer.release()
        }
    }
}



@SuppressLint("UnsafeOptInUsageError")
@Composable
fun rememberExoPlayer(context: Context, uri:String): ExoPlayer {
    return remember {
        ExoPlayer.Builder(context).build().apply {
            val httpDataSourceFactory = DefaultDataSourceFactory(context, "VideoPlay")
            val dashChunkSourceFactory = DefaultDashChunkSource.Factory(httpDataSourceFactory)
            val mediaSource = DashMediaSource.Factory(dashChunkSourceFactory, httpDataSourceFactory)
                .createMediaSource(MediaItem.fromUri(uri))
            prepare(mediaSource)
        }
    }
}