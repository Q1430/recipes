package com.recipe.recipes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val idCategory: String?,
    val strCategory: String?,
    val strCategoryThumb: String?,
    val strCategoryDescription: String?
)
@Serializable
data class CategoryListDto(
    @SerialName("meals")
    val categories: List<CategoryDto>
)