package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Category
import com.recipe.recipes.domain.repository.MealRepository
import com.recipe.recipes.util.OrderType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllCategoryUseCase @Inject constructor(
    private val mealRepository: MealRepository
) {
    operator fun invoke(orderType: OrderType):Flow<Result<List<Category>>>{
        return mealRepository.getAllCategories(orderType)
    }
}