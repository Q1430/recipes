package com.recipe.recipes.domain.model

/**
 * 代表一种成分及其用量。
 * @param name 成分名称
 * @param measure 用量
 */
data class Ingredient(
    val name: String,
    val measure: String?
)