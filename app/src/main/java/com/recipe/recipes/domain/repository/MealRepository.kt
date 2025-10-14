package com.recipe.recipes.domain.repository

import com.recipe.recipes.domain.model.Area
import com.recipe.recipes.domain.model.Category
import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.util.OrderType
import kotlinx.coroutines.flow.Flow

interface MealRepository {

    fun getRandomMeal(): Flow<Result<Meal>>

    fun getMealDetailsById(id: String):Flow<Result<Meal>>

    fun getFavoriteMeals(orderType: OrderType):Flow<List<Meal>>

    suspend fun saveFavoriteMeal(meal: Meal)

    suspend fun deleteFavoriteMeal(mealId: String)

    fun isMealFavorite(mealId: String): Flow<Boolean>

    fun getAllArea(orderType: OrderType):Flow<Result<List<Area>>>

    fun getMealsByArea(area:String):Flow<Result<List<Meal>>>

    fun getAllCategories(orderType: OrderType):Flow<Result<List<Category>>>

    fun getMealsByCategory(category:String):Flow<Result<List<Meal>>>

    fun getAllIngredients(orderType: OrderType):Flow<Result<List<Ingredient>>>

    fun getMealsByIngredient(ingredient: String): Flow<Result<List<Meal>>>

    fun getMealsByIngredients(ingredients: String):Flow<Result<List<Meal>>>

}