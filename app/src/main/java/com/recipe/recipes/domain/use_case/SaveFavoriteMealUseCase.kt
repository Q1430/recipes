package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import javax.inject.Inject

class SaveFavoriteMealUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(meal: Meal){
        repository.saveFavoriteMeal(meal)
    }
}