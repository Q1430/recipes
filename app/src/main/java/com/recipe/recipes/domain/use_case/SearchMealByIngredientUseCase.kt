package com.recipe.recipes.domain.use_case

import androidx.room.Insert
import com.recipe.recipes.domain.repository.MealRepository
import javax.inject.Inject

class SearchMealByIngredientUseCase @Inject constructor(
    private val repository: MealRepository
) {
}