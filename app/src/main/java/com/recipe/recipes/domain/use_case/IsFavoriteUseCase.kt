package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(mealId: String): Flow<Boolean>{
        return repository.isMealFavorite(mealId)
    }
}