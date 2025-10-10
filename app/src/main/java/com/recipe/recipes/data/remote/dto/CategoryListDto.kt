package com.recipe.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    val idCategory: String?,
    val strCategory: String?,
    val srtCategoryThumb: String?,
    val strCategoryDescription: String?
)
@Serializable
data class CategoryListDto(
    val categories: List<CategoryDto>
)