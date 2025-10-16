package com.recipe.recipes.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import retrofit2.http.Query

@Composable
fun TopSearchBar(
    query: String,
    onQueryChange:(String)->Unit,
    onBackClick:()->Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackClick) {
            Icon(Icons.Default.ArrowBack, contentDescription = "返回")
        }
        Row(
            modifier = Modifier
                .weight(1f)
                .height(36.dp)
                .background(color = Color(0xFFF3F3F3), shape = RoundedCornerShape(18.dp))
                .padding(horizontal = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 使用 BasicTextField 实现无边框的输入效果
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                modifier = Modifier.weight(1f),
                singleLine = true,
                decorationBox = {innerTextField ->
                    Row (verticalAlignment = Alignment.CenterVertically){
                        if(query.isEmpty()){
                            Text("吃点啥...", color = Color.Gray)
                        }
                        innerTextField()
                    }
                }
            )
            if(query.isNotEmpty()){
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "清除",
                    modifier = Modifier
                        .size(16.dp)
                        .clickable { onQueryChange("") }
                )
            }
        }
    }
}