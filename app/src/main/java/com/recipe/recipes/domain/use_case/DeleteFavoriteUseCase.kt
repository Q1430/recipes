package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.repository.MealRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val repository: MealRepository
) {
    suspend operator fun invoke(mealId: String){
        repository.deleteFavoriteMeal(mealId)
    }

}