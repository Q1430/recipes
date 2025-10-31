package com.recipe.recipes.presentation.player

import androidx.annotation.OptIn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    videoUrl:String,
    playWhenReady:Boolean = true
){
    val context = LocalContext.current

    var isFullScreen by remember { mutableStateOf(false) }

    //remember确保实例在重组期间保持不变
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            repeatMode = Player.REPEAT_MODE_ONE
        }
    }

    //使用LaunchedEffect,只在url变化时才更新播放源
    LaunchedEffect(videoUrl) {
        val mediaItem = MediaItem.fromUri(videoUrl)
        exoPlayer.setMediaItem(mediaItem)
        exoPlayer.prepare()
        //自动播放
        exoPlayer.playWhenReady = playWhenReady
    }

    //处理生命周期，释放播放器
    DisposableEffect(exoPlayer) {
        onDispose {

            exoPlayer.release()
        }
    }

    AndroidView(
        modifier = if(isFullScreen){
            Modifier.fillMaxSize()
        }else{
            Modifier.fillMaxWidth().height(300.dp)
        },
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                //配置控制器
                useController = false
                //全屏按钮监听
                setFullscreenButtonClickListener {
                    TODO("全屏逻辑")
                }
            }
        },
        update = {view ->
            if(isFullScreen){
                view.showController()
            }
        }
    )

    exoPlayer.addListener(object : Player.Listener{
        //监听播放状态
        override fun onPlaybackStateChanged(playbackState: Int) {
            when(playbackState){
                Player.STATE_BUFFERING ->{ TODO("显示加载动画")}
                Player.STATE_ENDED ->{ TODO("播放结束")}
            }
        }

        override fun onPositionDiscontinuity(
            oldPosition: Player.PositionInfo,
            newPosition: Player.PositionInfo,
            reason: Int
        ) {
            val currentPos = exoPlayer.currentPosition//实时获取进度
        }
    })
}