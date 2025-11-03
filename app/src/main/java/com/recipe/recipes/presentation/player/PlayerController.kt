package com.recipe.recipes.presentation.player.com.recipe.recipes.presentation.player

import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlayerController(
    modifier: Modifier = Modifier,
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

}