package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRandomMealUseCase @Inject constructor(
    private val repository: MealRepository
) {
     operator fun invoke(): Flow<Result<Meal>>{
        return repository.getRandomMeal()
    }
}