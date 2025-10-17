package com.recipe.recipes.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterChip(
    text:String,
    isSelected: Boolean,
    onClick:() -> Unit
){
    val backgroundColor = if (isSelected) Color(0xFFFFFBE5)else Color(0xFFF3F3F3)
    val contentColor = if (isSelected) Color(0xFFE5A700)else Color.Gray

    Row(
        modifier = Modifier
            .height(32.dp)
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .clickable { onClick = onClick },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(text, color = contentColor, fontSize = 13.sp)
        Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = contentColor)
    }
}