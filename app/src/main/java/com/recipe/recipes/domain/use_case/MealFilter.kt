package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal

sealed class MealFilter {
    data class ByCategory(val category:String):MealFilter()
    data class ByArea(val area:String):MealFilter()
    data class ByIngredient(val ingredient: String):MealFilter()
}