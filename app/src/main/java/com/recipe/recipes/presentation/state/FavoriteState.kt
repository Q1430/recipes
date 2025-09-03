package com.recipe.recipes.presentation.state

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.util.OrderType

data class FavoriteState(
    val favoriteMeals: List<Meal> = emptyList(),
    // 本地数据加载很快，通常可以省略 isLoading 状态
    val orderType: OrderType = OrderType.Descending
)