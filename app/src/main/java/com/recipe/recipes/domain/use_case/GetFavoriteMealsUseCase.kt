package com.recipe.recipes.domain.use_case

import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import com.recipe.recipes.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetFavoriteMealsUseCase @Inject constructor(
    private val repository: MealRepository
) {
    operator fun invoke(orderType: OrderType): Flow<List<Meal>> {
        // Use Case 的职责就是直接调用 Repository，把参数传进去即可
        // 所有复杂的排序逻辑都被封装在了下层
        return repository.getFavoriteMeals(orderType)
    }
}