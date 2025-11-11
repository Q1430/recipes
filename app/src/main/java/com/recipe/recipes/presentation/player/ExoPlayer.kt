package com.recipe.recipes.presentation.player

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.recipe.recipes.presentation.player.com.recipe.recipes.presentation.player.PlayerController
import kotlinx.coroutines.delay

@OptIn(UnstableApi::class)
@Composable
fun ExoPlayer(
    modifier: Modifier = Modifier,
    videoUrl:String = "http://vjs.zencdn.net/v/oceans.mp4",
    playWhenReady:Boolean = true
) {
    val context = LocalContext.current
    var isFullScreen by remember { mutableStateOf(false) }

    //remember确保实例在重组期间保持不变
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }
    //播放状态
    var playbackState by remember { mutableStateOf(Player.STATE_IDLE) }
    var isPlaying by remember { mutableStateOf(exoPlayer.isPlaying) }
    var currentPosition by remember { mutableStateOf(0L) }
    var duration by remember { mutableStateOf(0L) }

    //使用LaunchedEffect,只在url变化时才更新播放源
    LaunchedEffect(videoUrl) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        //自动播放
        exoPlayer.playWhenReady = playWhenReady
    }
    //只在播放时更新进度
    LaunchedEffect(isPlaying,playbackState) {
        while (isPlaying && playbackState == Player.STATE_READY) {
            currentPosition = exoPlayer.currentPosition
            delay(1000)
        }
    }
    //全屏逻辑,当isFullScreen改变时控制Activity
    LaunchedEffect(isFullScreen) {
        val activity = context.findActivity()?:return@LaunchedEffect
        val window = activity.window ?: return@LaunchedEffect
        val insetsController = WindowCompat.getInsetsController(window,window.decorView)

        if (isFullScreen){
            //隐藏系统栏
            insetsController.apply {
                hide(WindowInsetsCompat.Type.systemBars())
                systemBarsBehavior = WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
            //强制横屏
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }else{
            insetsController.show(WindowInsetsCompat.Type.systemBars())
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    //退出Composable时清理全屏状态
    DisposableEffect(Unit) {
        onDispose {
            val activity = context.findActivity() ?:return@onDispose
            //确保退出时恢复竖屏和显示系统栏
            if (activity.requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE){
                activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }
            val window = activity.window?:return@onDispose
            val insetsController = WindowCompat.getInsetsController(window,window.decorView)
            insetsController.show(WindowInsetsCompat.Type.systemBars())
        }
    }

    //处理生命周期，释放播放器
    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            //监听播放状态
            override fun onPlaybackStateChanged(state: Int) {
                playbackState = state
                if (state == Player.STATE_READY){
                    duration = exoPlayer.duration
                }
            }

            override fun onIsPlayingChanged(playing: Boolean) {
                isPlaying = playing
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.removeListener(listener)
            exoPlayer.release()
        }
    }

    Box(
        modifier = if (isFullScreen) {
            Modifier.fillMaxSize()
        } else {
            Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 9f)
        },
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    //配置控制器
                    useController = false
                }
            }
        )
        PlayerController(
            modifier = Modifier.fillMaxSize(), //TODO("后续研究研究")
            isPlaying = isPlaying,
            playbackState = playbackState,
            currentPosition = currentPosition,
            duration = duration,
            isFullScreen = isFullScreen,
            onPlayPauseClick = {
                if (exoPlayer.isPlaying) exoPlayer.pause() else exoPlayer.play()
            },
            onReplayClick = {
                exoPlayer.seekTo(0)
                exoPlayer.play()
            },
            onSeek = { positionMs ->
                exoPlayer.seekTo(positionMs)
            },
            onFullScreenClick = { shouldBeFullScreen ->
                isFullScreen = shouldBeFullScreen
            }
        )
        //根据播放器状态选择显示画面
//        if (playbackState == Player.STATE_BUFFERING) {
//            TODO("缓冲")
//        }
//        if (playbackState == Player.STATE_ENDED) {
//            TODO("结束")
//        }
    }
}
//辅助方法，获取Activity
private fun Context.findActivity(): Activity? = when(this){
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}

@Preview(showBackground = true)
@Composable
fun ExoplayerTest(){
    ExoPlayer()
}