package com.recipe.recipes.domain.model


/**
 * 代表一餐的领域模型，用于App的业务逻辑和UI。
 * @param id 餐品ID
 * @param name 名称
 * @param category 分类
 * @param area 地区
 * @param instructions 制作说明
 * @param thumbnailUrl 缩略图URL
 * @param youtubeUrl YouTube视频URL
 * @param tags 标签列表
 * @param ingredients 成分列表
 */
data class Meal(
    val id: String,
    val name: String,
    val category: String,
    val area: String,
    val instructions: List<String>,
    val thumbnailUrl: String,
    val youtubeUrl: String,
    val tags: List<String>,
    val ingredients: List<Ingredient>,
    val savedTimestamp: Long? = null

)
