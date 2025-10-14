package com.recipe.recipes.data.mapper

import com.recipe.recipes.data.remote.dto.IngredientDto
import com.recipe.recipes.domain.model.Ingredient

fun IngredientDto.toIngredient():Ingredient{
    return Ingredient(
        idIngredient = this.idIngredient,
        strIngredient = this.strIngredient?:""
    )
}