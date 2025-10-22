package com.recipe.recipes.presentation.player

import android.util.SparseArray
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile

@Composable
fun CustomExoPlayer(
    videoId:String,
    modifier: Modifier = Modifier
){
    val exoPlayer = rememberExoPlayer()
    val context = LocalContext.current

    //使用LaunchedEffect在后台执行“视频流地址提取”操作

    LaunchedEffect(videoId) {
        object :YouTubeExtractor(context){
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null){
                    val videoUrl = ytFiles[22].url

                    val mediaItem = MediaItem.fromUri(videoUrl)

                    //将MediaItem设置给 ExoPlayer并准备播放
                    exoPlayer.setMediaItem(mediaItem)
                    exoPlayer.prepare()
                    exoPlayer.playWhenReady = true
                }
            }
        }.extract(videoId)
    }
    //使用AndroidView来展示播放器ui
    AndroidView(
        modifier = modifier.fillMaxWidth(),
        factory = {
            PlayerView(it).apply {
                player = exoPlayer
            }
        }
    )
}
