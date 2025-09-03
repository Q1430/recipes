package com.recipe.recipes.presentation.favorite

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.recipe.recipes.domain.model.Meal

@Composable
fun FavoriteItem(
    modifier: Modifier = Modifier,
    meal: Meal,
    onItemClick:(String) -> Unit,
    onFavoriteClick:(String) -> Unit
){
    Card(
        modifier = Modifier
            .padding(8.dp)
            .clickable{ onItemClick(meal.id)},
        elevation = CardDefaults.cardElevation(defaultElevation =  4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AsyncImage(
                    model = meal.thumbnailUrl,
                    contentDescription = meal.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),//保持图片为正方形
                    contentScale = ContentScale.Crop//裁剪以填充空间
                )

                Text(
                    text = meal.name,
                    modifier = Modifier.padding(8.dp)
                )
            }
            IconButton(
                onClick = {onFavoriteClick(meal.id)},
                modifier = Modifier.align(Alignment.TopEnd)
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = "取消收藏",
                    tint = Color.Red,
                    modifier = Modifier
                        .padding(4.dp)
                )
            }
        }
    }

}
