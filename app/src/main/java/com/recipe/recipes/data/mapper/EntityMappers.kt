package com.recipe.recipes.data.mapper

import com.recipe.recipes.data.local.entity.MealEntity
import com.recipe.recipes.domain.model.Meal

/**
 * 将领域模型 Meal 转换为数据库实体 MealEntity。
 * 这是在“保存到收藏夹”时使用的。
 */
fun Meal.toEntity(): MealEntity {
    return MealEntity(
        id = this.id,
        name = this.name,
        category = this.category,
        area = this.area,
        instructions = this.instructions,
        thumbnailUrl = this.thumbnailUrl,
        tags = this.tags, // 直接赋值
        youtubeUrl = this.youtubeUrl,
        ingredients = this.ingredients, // 直接赋值
        savedTimestamp = System.currentTimeMillis()
    )
}

/**
 * 将数据库实体 MealEntity 转换为领域模型 Meal。
 *这是在从“收藏夹”读取数据后，给上层使用时调用的。
 */
fun MealEntity.toMeal(): Meal {
    return Meal(
        id = this.id.toString(),
        name = this.name,
        category = this.category,
        area = this.area,
        instructions = this.instructions,
        thumbnailUrl = this.thumbnailUrl,
        tags = this.tags, // 直接赋值
        youtubeUrl = this.youtubeUrl,
        ingredients = this.ingredients, // 直接赋值
        savedTimestamp = this.savedTimestamp
    )
}