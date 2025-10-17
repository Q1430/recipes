package com.recipe.recipes.presentation.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FilterButton(
    text:String,
    isSelected:Boolean,
    onClick:() -> Unit
){
    val backgroundColor = if (isSelected) Color(0xFFFFFBE5)else Color(0xFFF3F3F3)
    val contentColor = if (isSelected) Color(0xFFE5A700)else Color.Gray

    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = contentColor
        ),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Text(text, fontSize = 13.sp)
    }
}