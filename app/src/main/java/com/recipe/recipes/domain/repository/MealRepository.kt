package com.recipe.recipes.domain.repository

import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.util.OrderType
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    fun getRandomMeal(): Flow<Result<Meal>>

    fun searchMealsByIngredient(ingredient: String): Flow<Result<List<Meal>>>

    fun getMealDetailsById(id: String):Flow<Result<Meal>>

    fun getFavoriteMeals(orderType: OrderType):Flow<List<Meal>>

    suspend fun saveFavoriteMeal(meal: Meal)

    suspend fun deleteFavoriteMeal(mealId: String)

    fun isMealFavorite(mealId: String): Flow<Boolean>
}