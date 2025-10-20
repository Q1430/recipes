package com.recipe.recipes.presentation.state

import com.recipe.recipes.domain.model.Area
import com.recipe.recipes.domain.model.Category
import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal

enum class FilterType{
    AREA,INGREDIENT,CATEGORY
}

data class SearchState(
    val searchQuery: String = "",       // 当前搜索框中的文本
    val isLoading: Boolean = false,
    val searchResults: List<Meal> = emptyList(),
    val error: String? = null,

    //筛选条件
    val selectedArea:String ?= "Chinese",
    val selectedCategory:String ?= null,
    val selectedIngredients:Set<String> = emptySet(),
    //所有弹窗都关闭
    val expandedFilter:FilterType ?= null,//null表示所有弹窗都关闭

    val allAreas:List<Area> = emptyList(),
    val allCategories:List<Category> = emptyList(),
    val allIngredients:List<Ingredient> = emptyList()
)