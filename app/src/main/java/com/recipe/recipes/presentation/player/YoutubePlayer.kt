
package com.recipe.recipes.presentation.player

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerCallback
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import androidx.core.net.toUri


@Composable
fun YoutubePlayer(
    uri:String,
    modifier: Modifier = Modifier,
    lifecycleOwner:LifecycleOwner = LocalLifecycleOwner.current
){
    //暂时写在ui中，后边改到viewmodel里
    val videoId = uri.toUri().getQueryParameter("v")
    AndroidView(
        modifier = modifier,
        factory = {context ->
            YouTubePlayerView(context).apply {
                lifecycleOwner.lifecycle.addObserver(this)

                this.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        if (videoId != null) {
                            youTubePlayer.loadVideo(videoId,0f)
                        }
                    }
                })
            }
        },
        update = { view ->
            // 当 videoId 改变时，加载新视频
            view.getYouTubePlayerWhenReady(object:YouTubePlayerCallback{
                override fun onYouTubePlayer(youTubePlayer: YouTubePlayer) {
                    if (videoId != null) {
                        youTubePlayer.loadVideo(videoId,0f)
                    }
                }
            })
        }
    )
}

@Preview(showBackground = true)
@Composable
fun Youtube(){
    YoutubePlayer(
        uri ="https://www.youtube.com/watch?v=1IszT_guI08"
    )
}