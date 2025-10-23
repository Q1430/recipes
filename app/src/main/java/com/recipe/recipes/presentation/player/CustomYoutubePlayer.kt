package com.recipe.recipes.presentation.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

@Composable
fun CustomYoutubePlayer(
    videoId:String,
    modifier: Modifier = Modifier,
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
){
    val context = LocalContext.current

    var playerInstance:YouTubePlayer ?by remember { mutableStateOf(null) }

    //跟踪播放器状态
    var isPlaying by remember { mutableStateOf(false) }
    var totalDuration by remember { mutableStateOf(0f) }
    var currentTime by remember { mutableStateOf(0f)}

    //跟踪用户是否拖动进度条
    var isDraggingSlider by remember { mutableStateOf(false) }
    var sliderPosition by remember { mutableStateOf(currentTime)}

    Box(modifier = Modifier){
        AndroidView(
            modifier = Modifier.matchParentSize(),
            factory = {
                YouTubePlayerView(context).apply {
                    lifecycleOwner.lifecycle.addObserver(this)
                    //关闭自动初始化
                    enableAutomaticInitialization = false

                    //定义监听器
                    val listener = object:AbstractYouTubePlayerListener(){
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            playerInstance = youTubePlayer
                            youTubePlayer.loadVideo(videoId,0f)
                        }

                        //监听播放器状态
                        override fun onStateChange(
                            youTubePlayer: YouTubePlayer,
                            state: PlayerConstants.PlayerState
                        ) {
                            isPlaying = state == PlayerConstants.PlayerState.PLAYING
                        }

                        //监听视频总时长
                        override fun onVideoDuration(
                            youTubePlayer: YouTubePlayer,
                            duration: Float
                        ) {
                            totalDuration = duration
                        }

                        //监听当前播放秒数
                        override fun onCurrentSecond(youTubePlayer: YouTubePlayer, second: Float) {
                            if(!isDraggingSlider){
                                currentTime = second
                            }
                        }
                    }

                    //隐藏所有控件
                    val options = IFramePlayerOptions.Builder(context)
                        .controls(0)// 0为隐藏所有控件
                        .fullscreen(0)
                        .build()

                    //使用监听器和选项进行初始化
                    initialize(listener,options)
                }
            },
            update = {view ->
                view.getYouTubePlayerWhenReady(object :YouTubePlayerCallback{
                    override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(videoId,0f)
                    }
                })
            }
        )
        //覆盖在播放器上的ui
        Column (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp)
        ){
            //自定义进度条
            Slider(
                value = if (isDraggingSlider) sliderPosition else currentTime,
                onValueChange = {newPosition ->
                    isDraggingSlider = true
                    sliderPosition = newPosition
                },
                //松手时触发
                onValueChangeFinished = {
                    isDraggingSlider = false
                    playerInstance?.seekTo(sliderPosition)
                    playerInstance?.play()
                },
                valueRange = 0f..totalDuration.coerceAtLeast(0f),
                colors = SliderDefaults.colors(
                    thumbColor = Color.Yellow,
                    activeTrackColor = Color.Red,
                    inactiveTickColor = Color.White.copy(alpha = 0.5f)
                )
            )
            IconButton(
                onClick = {
                    if (isPlaying){
                        playerInstance?.pause()
                    }else{
                        playerInstance?.play()
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                    contentDescription = "播放/暂停",
                    tint = Color.White
                )
            }
        }
    }




}