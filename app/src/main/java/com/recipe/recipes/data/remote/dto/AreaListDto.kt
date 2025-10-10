package com.recipe.recipes.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class AreaListDto(
    val Areas: List<Area>
)


data class Area(
    val strArea: String?
)