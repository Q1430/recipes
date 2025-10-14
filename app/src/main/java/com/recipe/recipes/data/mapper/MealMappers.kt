package com.recipe.recipes.data.mapper


import com.recipe.recipes.data.remote.dto.MealDto
import com.recipe.recipes.data.remote.dto.MealListDto
import com.recipe.recipes.domain.model.Ingredient
import com.recipe.recipes.domain.model.Meal

fun MealDto.toMeal(): Meal{
    val ingredientsAndMeasures = listOf(
        strIngredient1 to strMeasure1,
        strIngredient2 to strMeasure2,
        strIngredient3 to strMeasure3,
        strIngredient4 to strMeasure4,
        strIngredient5 to strMeasure5,
        strIngredient6 to strMeasure6,
        strIngredient7 to strMeasure7,
        strIngredient8 to strMeasure8,
        strIngredient9 to strMeasure9,
        strIngredient10 to strMeasure10,
        strIngredient11 to strMeasure11,
        strIngredient12 to strMeasure12,
        strIngredient13 to strMeasure13,
        strIngredient14 to strMeasure14,
        strIngredient15 to strMeasure15,
        strIngredient16 to strMeasure16,
        strIngredient17 to strMeasure17,
        strIngredient18 to strMeasure18,
        strIngredient19 to strMeasure19,
        strIngredient20 to strMeasure20
    )


    // 2. 过滤掉成分名称为空或null的条目，然后将它们映射到 Ingredient 数据类
    val ingredientsList = ingredientsAndMeasures
        .filter { (ingredient, _) -> !ingredient.isNullOrBlank() }
        .map { (ingredient, measure) ->
            Ingredient(
                name = ingredient!!, // 我们已经过滤了null和blank，所以这里用!!是安全的
                measure = measure.orEmpty() // 如果用量是null，则转换为空字符串
            )
        }
    //将strInstructions转换成strInstructionList
    val instructionSteps = this.strInstructions
        ?.split("\r\n")
        ?.map { it.trim() }
        ?.filter { it.isNotEmpty() }
        ?: emptyList()



    // 3. 将 strTags 字符串按逗号分割成一个列表，并去除每个标签前后的空格
    val tagsList = strTags?.split(",")?.map { it.trim() } ?: emptyList()

    // 4. 创建并返回最终的、干净的 Meal 对象
    return Meal(
        id = this.idMeal,
        name = this.strMeal ?: "无标题", // 如果strMeal是null，提供一个默认值
        category = this.strCategory ?: "未分类",
        area = this.strArea ?: "未知地区",
        instructions = instructionSteps,
        thumbnailUrl = this.strMealThumb ?: "",
        tags = tagsList,
        youtubeUrl = this.strYoutube ?: "",
        ingredients = ingredientsList
    )

}