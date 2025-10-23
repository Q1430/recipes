package com.recipe.recipes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.recipe.recipes.presentation.MainScreen
import com.recipe.recipes.presentation.discovery.DiscoveryFindScreen
import com.recipe.recipes.presentation.discovery.NavGraphD
import com.recipe.recipes.presentation.player.CustomYoutubePlayer
import com.recipe.recipes.presentation.player.YoutubePlayer
import com.recipe.recipes.presentation.search.SearchScreen
import com.recipe.recipes.presentation.viewmodel.DiscoveryViewModel
import com.recipe.recipes.ui.theme.RecipesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val discoveryViewModel: DiscoveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RecipesTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    color = MaterialTheme.colorScheme.background
                ){
                Column(
                    modifier = Modifier.fillMaxWidth(), // 占满宽度
                    verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    YoutubePlayer(
                        uri = "https://www.youtube.com/watch?v=1IszT_guI08"
                    )
//                    CustomYoutubePlayer(
//                        videoId = "aqz-KE-bpKQ",
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .aspectRatio(16 / 9f)
//                            .padding(top = 100.dp)
//                    )
                }
                }
            }
        }
    }
}