package com.recipe.recipes.presentation.player.com.recipe.recipes.presentation.player

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Fullscreen
import androidx.compose.material.icons.filled.FullscreenExit
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.media3.common.Player

@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
    //状态
    isPlaying:Boolean,
    playbackState:Int,
    currentPosition:Long,
    duration: Long,
    isFullScreen:Boolean,
    //回调
    onPlayPauseClick:() -> Unit,
    onReplayClick:()-> Unit,
    onSeek:(Long) -> Unit,
    onFullScreenClick:(Boolean) -> Unit
){
    Box(modifier = modifier) {
        when {
            // 播放结束时，显示重播按钮
            playbackState == Player.STATE_ENDED -> {
                IconButton(
                    onClick = onReplayClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(Icons.Default.Replay, "Replay", tint = Color.White)
                }
            }
            // 正在播放时，显示暂停
            isPlaying -> {
                IconButton(
                    onClick = onPlayPauseClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(Icons.Default.Pause, "Pause", tint = Color.White)
                }
            }
            // 暂停时，显示播放
            else -> {
                IconButton(
                    onClick = onPlayPauseClick,
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Icon(Icons.Default.PlayArrow, "Play", tint = Color.White)
                }
            }
        }


        //底部控制栏
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            //时间
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(formatDuration(currentPosition), color = Color.White)
                Text(formatDuration(duration), color = Color.White)
            }

            Slider(
                value = currentPosition.toFloat(),
                onValueChange = { onSeek(it.toLong()) },
                valueRange = 0f..duration.toFloat()
            )

            IconButton(
                onClick = { onFullScreenClick(!isFullScreen) },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(
                    if (!isFullScreen) Icons.Default.Fullscreen else Icons.Default.FullscreenExit,
                    contentDescription = "Full Screen",
                    tint = Color.White
                )
            }
        }
    }
}
//转换时间
private fun formatDuration(ms: Long): String {
    val totalSeconds = ms / 1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60
    return String.format("%02d:%02d", minutes, seconds)
}