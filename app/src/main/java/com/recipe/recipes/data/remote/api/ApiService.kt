package com.recipe.recipes.data.remote.api

import com.recipe.recipes.data.remote.dto.CategoryListDto
import com.recipe.recipes.data.remote.dto.MealListDto
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 定义所有与 TheMealDB API 的网络通信端点
 */
interface ApiService {

    /**
     * 获取一道随机菜谱
     * 当用户不知道吃什么时，可以调用这个接口来获取灵感。
     * API 端点: random.php
     */
    @GET("api/json/v1/1/random.php")
    suspend fun getRandomMeal(): MealListDto

    /**
     * 根据单个主要食材进行搜索
     * 当用户知道有什么材料时，可以调用这个接口来匹配菜品。
     * API 端点: filter.php?i={ingredient}
     * @param ingredient 用户输入的食材名称，例如 "chicken_breast"
     */
    @GET("api/json/v1/1/filter.php")
    suspend fun searchMealsByIngredient(@Query("i") ingredient: String): MealListDto

    /**
     * 获取菜谱的完整详情
     * 无论是在发现还是在搜索列表中，当用户点击某个菜品时，
     * 我们需要用它的ID来获取完整的做法、食材列表等详细信息。
     * API 端点: lookup.php?i={mealId}
     * @param mealId 菜谱的唯一ID
     */
    @GET("api/json/v1/1/lookup.php")
    suspend fun getMealDetailsById(@Query("i") mealId: String): MealListDto

    /**
     * 列出所有餐食类别
     * API端点 categories.php
     */
    @GET("api/json/v1/1/categories.php")
    suspend fun getAllCategories(): CategoryListDto

    /**
     * 按类别过滤
     * API端点 filter.php?c={category}
     * @param category 种类
     */
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByCategory(@Query("c") category: String): MealListDto

    /**
     * 按区域过滤
     * API filter.php?a={area}
     */
    @GET("api/json/v1/1/filter.php")
    suspend fun getMealsByArea(@Query("a") area: String): MealListDto


    @GET("api/json/v1/1/list.php")
    suspend fun getAllArea(): AreaListDto
    /**
     * 按多种成分筛选
     * API端点 filter.php?i={ingredientList}
     */
}