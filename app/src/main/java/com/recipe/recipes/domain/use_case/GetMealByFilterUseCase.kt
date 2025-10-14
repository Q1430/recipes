package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMealByFilterUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(filter: MealFilter): Flow<Result<List<Meal>>>{
        return when(filter){
            is MealFilter.ByCategory -> mealRepository.getMealsByCategory(filter.category)
            is MealFilter.ByArea -> mealRepository.getMealsByArea(filter.area)
            is MealFilter.ByIngredient -> mealRepository.getMealsByIngredient(filter.ingredient)
        }
    }
}