package com.recipe.recipes.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.recipe.recipes.data.local.entity.MealEntity

@Database(
    entities = [MealEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class FavoriteMealDatabase: RoomDatabase() {
    abstract fun favoriteMealDao(): FavoriteMealDao

}