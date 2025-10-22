package com.recipe.recipes.presentation.player

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun  rememberExoPlayer():Player{
    val context = LocalContext.current

    //使用remember来确保ExoPlayer实例在重组之间被复用
    val expPlayer = remember{
        ExoPlayer.Builder(context).build()
    }
    //使用DisposableEffect来在Composable销毁时释放资源
    DisposableEffect(expPlayer) {
        onDispose {
            expPlayer.release()
        }
    }

    return expPlayer
}