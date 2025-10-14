package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealByAreaUseCase @Inject constructor(
    private val mealRepository: MealRepository
){
    operator fun invoke(area: String): Flow<Result<List<Meal>>> {
        return mealRepository.getMealsByCategory(area)
    }
}