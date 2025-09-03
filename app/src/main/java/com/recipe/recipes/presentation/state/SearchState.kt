package com.recipe.recipes.presentation.state

import com.recipe.recipes.domain.model.Meal

data class SearchState(
    val searchQuery: String = "",       // 当前搜索框中的文本
    val isLoading: Boolean = false,
    val searchResults: List<Meal> = emptyList(),
    val error: String? = null
)