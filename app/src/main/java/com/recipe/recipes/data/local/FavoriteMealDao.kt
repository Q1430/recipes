package com.recipe.recipes.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.recipe.recipes.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMealDao {
    /**
     *
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMeal(meal: MealEntity)


    @Query("DELETE FROM favorite_meals WHERE id = :mealId")
    suspend fun deleteMeal(mealId: String)

    @Query("SELECT * FROM favorite_meals ORDER BY name ASC ")
    fun getAllFavoriteMeals(): Flow<List<MealEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_meals WHERE id = :mealId)")
    fun isFavorite(mealId: String): Flow<Int>

    @Query("SELECT * FROM favorite_meals ORDER BY savedTimestamp DESC")
    fun getAllFavoriteMealsDesc(): Flow<List<MealEntity>>

}