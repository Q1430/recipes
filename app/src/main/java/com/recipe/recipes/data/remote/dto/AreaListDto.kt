package com.recipe.recipes.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AreaListDto(
    @SerialName("meals")
    val areas: List<Area>?
)

@Serializable
data class Area(
    val strArea: String?
)