package com.recipe.recipes.presentation.state

import com.recipe.recipes.domain.model.Meal

/**
 * 代表了菜谱详情页在任意时刻的所有状态
 */
data class MealState(
    val isLoading: Boolean = false,    // 是否正在加载
    val meal: Meal? = null,            // 成功获取到的菜谱数据
    val isFavorite: Boolean = false,   // 当前菜谱是否已被收藏
    val error: String? = null,          // 是否发生了错误，以及错误信息
    val isOrderSectionVisible: Boolean = false
)
