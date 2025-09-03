package com.recipe.recipes.presentation.state

import com.recipe.recipes.domain.model.Meal

data class DiscoveryState(
    val isLoading: Boolean = false,
    val randomMeal: Meal? = null,
    val error: String? = null
)