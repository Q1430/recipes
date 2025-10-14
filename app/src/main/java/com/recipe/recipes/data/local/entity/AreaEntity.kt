package com.recipe.recipes.data.local.entity

import androidx.room.Entity
import com.recipe.recipes.data.remote.dto.Area

@Entity
data class AreaEntity(
    val strArea: String?
)
