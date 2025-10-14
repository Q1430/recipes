package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealByIngredientUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(strIngredient:String): Flow<Result<List<Meal>>>{
        return repository.getMealsByIngredient(strIngredient)
    }
}