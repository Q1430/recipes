package com.recipe.recipes.presentation.search

import android.widget.ImageButton
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.recipe.recipes.domain.model.Meal

@Composable
fun ResultItem(
    meal:Meal,
    onItemClick:(String) -> Unit,
    onFavoriteClick:(String) ->Unit,
    isFavorite:Boolean
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(meal.id) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = meal.thumbnailUrl,
                contentDescription = meal.name,
                modifier = Modifier
                    .aspectRatio(1f)
                    .weight(2f)
                    .clip(RoundedCornerShape(8.dp)),
            )
            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .padding(end = 8.dp,start = 8.dp)
                    .weight(3f),
            ) {
                Text(
                    text = meal.name,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.MiddleEllipsis,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                Text(
                    text = meal.tags.toString(),
                    fontSize = 8.sp,
                    maxLines = 2,
                    overflow = TextOverflow.MiddleEllipsis,
                    modifier = Modifier.align(alignment = Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.End
            ) {  }
                AsyncImage(
                    model = meal.area,
                    contentDescription = meal.area,
                    alignment = Alignment.TopEnd
                )
                IconButton(
                    onClick = { onFavoriteClick(meal.id) },
                    modifier = Modifier.align(Alignment.Bottom)
                ) {
                    Icon(
                        imageVector = if(isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "取消收藏",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(4.dp)
                    )
                }
        }
    }

}