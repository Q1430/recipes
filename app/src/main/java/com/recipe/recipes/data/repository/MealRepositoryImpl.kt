package com.recipe.recipes.data.repository

import com.recipe.recipes.data.local.FavoriteMealDao
import com.recipe.recipes.data.remote.api.ApiService
import com.recipe.recipes.data.mapper.toEntity
import com.recipe.recipes.data.mapper.toMeal
import com.recipe.recipes.domain.model.Area
import com.recipe.recipes.domain.model.Category
import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal
import com.recipe.recipes.domain.repository.MealRepository
import com.recipe.recipes.util.OrderType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MealRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val dao: FavoriteMealDao
) : MealRepository{
    override fun getRandomMeal(): Flow<Result<Meal>>  = flow{
        try {
            val mealDto = apiService.getRandomMeal().meals?.firstOrNull()
            if (mealDto != null) {
                emit(Result.success(mealDto.toMeal()))
            } else {
                emit(Result.failure(Exception("No meal found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }

    override fun getMealsByIngredient(ingredient: String): Flow<Result<List<Meal>>>  = flow{
//        try {
//            val mealListDto = apiService.getMealsByIngredient(ingredient).meals
//            if (!mealListDto.isNullOrEmpty()) {
//                emit(Result.success(mealListDto.map{it.toMeal()}))
//            } else {
//                emit(Result.failure(Exception("No meal found")))
//            }
//        } catch (e: Exception) {
//            emit(Result.failure(e))
//        }
    }

    override fun getMealsByIngredients(ingredients: List<Ingredient>): Flow<Result<List<Meal>>> {
        TODO("Not yet implemented")
    }

    override fun getMealDetailsById(id: String): Flow<Result<Meal>>  = flow{
        try {
            val mealDto = apiService.getMealDetailsById(id).meals?.firstOrNull()
            if (mealDto != null) {
                emit(Result.success(mealDto.toMeal()))
            } else {
                emit(Result.failure(Exception("No meal found")))
            }
        } catch (e: Exception) {
            emit(Result.failure(e))
        }
    }


    override fun getFavoriteMeals(orderType: OrderType): Flow<List<Meal>> {
        val flowOfEntities = when (orderType) {
            is OrderType.Ascending -> dao.getAllFavoriteMeals()
            is OrderType.Descending -> dao.getAllFavoriteMealsDesc()
        }
        return flowOfEntities.map { entities ->
            entities.map { it.toMeal() }
        }
    }

    override suspend fun saveFavoriteMeal(meal: Meal) {
        dao.insertMeal(meal.toEntity())
    }

    override suspend fun deleteFavoriteMeal(mealId: String) {
        dao.deleteMeal(mealId)
    }

    override fun isMealFavorite(mealId: String): Flow<Boolean> {
        return dao.isFavorite(mealId).map { it == 1 } // 将 1/0 转换为 true/false
    }

    override fun getAllArea(orderType: OrderType): Flow<Result<List<Area>>> {
        TODO("Not yet implemented")
    }

    override fun getMealsByArea(area: String): Flow<Result<List<Meal>>> {
        TODO("Not yet implemented")
    }

    override fun getAllCategories(orderType: OrderType): Flow<Result<List<Category>>> {
        TODO("Not yet implemented")
    }

    override fun getMealsByCategory(category: String): Flow<Result<List<Meal>>> {
        TODO("Not yet implemented")
    }

    override fun getAllIngredients(orderType: OrderType): Flow<Result<List<Ingredient>>> {
        TODO("Not yet implemented")
    }

}