package com.recipe.recipes.presentation

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val label: String,       // 按钮下方的文字
    val icon: ImageVector,     // 按钮的图标
    val route: String        // 点击后要跳转的路由地址
)