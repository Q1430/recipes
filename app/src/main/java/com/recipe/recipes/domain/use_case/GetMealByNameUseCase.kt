package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class GetMealByNameUseCase @Inject constructor(
    private val mealRepository: MealRepository
){
    operator fun invoke(name:String): Flow<Result<List<Meal>>>{
        if (name.isBlank()){
            return flowOf(Result.success(emptyList()))
        }
        return mealRepository.getMealByName(name)
    }
}