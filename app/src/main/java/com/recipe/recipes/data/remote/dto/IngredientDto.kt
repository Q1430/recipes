package com.recipe.recipes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class IngredientDto(
    val idIngredient:String?,
    val strIngredient:String?,
)
@Serializable
data class IngredientListDto(
    @SerialName("meals")
    val ingredients:List<IngredientDto>
)