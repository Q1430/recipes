package com.recipe.recipes.data.mapper

import com.recipe.recipes.data.remote.dto.CategoryDto
import com.recipe.recipes.domain.model.Category
import com.recipe.recipes.domain.model.CategoryList

fun CategoryDto.toCategory():Category {
    return Category(
        idCategory = this.idCategory?:"",
        strCategory = this.strCategory?:"未分类",
        strCategoryThumb = this.strCategoryThumb?:"",
        strCategoryDescription = this.strCategoryDescription?:""
    )
}