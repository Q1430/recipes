package com.recipe.recipes.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.recipe.recipes.domain.model.Ingredient

@Entity(tableName = "favorite_meals")
data class MealEntity(
    @PrimaryKey val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: List<String>,
    val thumbnailUrl: String,
    val youtubeUrl: String,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
    val savedTimestamp: Long
)
